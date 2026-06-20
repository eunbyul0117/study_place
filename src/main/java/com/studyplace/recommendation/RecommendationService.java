package com.studyplace.recommendation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studyplace.place.FocusLevel;
import com.studyplace.place.NoiseLevel;
import com.studyplace.place.Place;
import com.studyplace.place.PlaceRepository;
import com.studyplace.place.SizeLevel;
import com.studyplace.recommendation.dto.AiRecommendationResult;
import com.studyplace.recommendation.dto.RecommendationRequest;
import com.studyplace.recommendation.dto.RecommendationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationService {

    private static final int CANDIDATE_SIZE = 8;
    private static final int RESULT_SIZE = 3;

    private static final String SYSTEM_PROMPT = """
            너는 스터디 장소 추천 도우미야.
            반드시 아래 JSON 형식으로만 응답해. 다른 설명이나 마크다운 코드블록은 절대 포함하지 마.
            {
              "recommendations": [
                { "placeId": 숫자, "reason": "한국어 추천 이유" }
              ]
            }
            placeId는 반드시 사용자가 제공한 후보 목록의 id 중에서만 골라야 해.
            정확히 3개를 추천해. 후보가 3개 미만이면 있는 만큼만 추천해.
            """;

    private final PlaceRepository placeRepository;
    private final GroqClient groqClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<RecommendationResponse> recommendPlaces(
            RecommendationRequest request
    ) {
        List<ScoredPlace> candidates = placeRepository.findAll()
                .stream()
                .map(place -> {
                    Double distanceKm = calculateDistanceKm(place, request);
                    int score = calculateScore(place, request, distanceKm);
                    return new ScoredPlace(place, score, distanceKm);
                })
                .sorted(Comparator.comparingInt(ScoredPlace::score).reversed())
                .limit(CANDIDATE_SIZE)
                .toList();

        if (candidates.isEmpty()) {
            return List.of();
        }

        try {
            return recommendWithAi(candidates, request);
        } catch (Exception e) {
            log.error("AI 추천 실패, 기존 점수 기반 로직으로 대체합니다.", e);
            return fallbackRecommend(candidates);
        }
    }

    private List<RecommendationResponse> recommendWithAi(
            List<ScoredPlace> candidates,
            RecommendationRequest request
    ) throws Exception {

        String userPrompt = buildUserPrompt(candidates, request);

        String aiResponse = groqClient.chat(SYSTEM_PROMPT, userPrompt, true);
        String sanitized = sanitizeJson(aiResponse);

        AiRecommendationResult result =
                objectMapper.readValue(sanitized, AiRecommendationResult.class);

        Map<Long, ScoredPlace> candidateMap = candidates.stream()
                .collect(Collectors.toMap(
                        sp -> sp.place().getId(),
                        sp -> sp
                ));

        List<RecommendationResponse> responses = result.getRecommendations()
                .stream()
                .filter(item -> candidateMap.containsKey(item.getPlaceId()))
                .limit(RESULT_SIZE)
                .map(item -> {
                    ScoredPlace sp = candidateMap.get(item.getPlaceId());
                    return new RecommendationResponse(
                            sp.place(),
                            item.getReason(),
                            sp.distanceKm()
                    );
                })
                .toList();

        if (responses.isEmpty()) {
            throw new IllegalStateException("AI가 유효한 추천을 반환하지 않았습니다.");
        }

        return responses;
    }

    private List<RecommendationResponse> fallbackRecommend(
            List<ScoredPlace> candidates
    ) {
        return candidates.stream()
                .limit(RESULT_SIZE)
                .map(sp -> new RecommendationResponse(
                        sp.place(),
                        buildBaseReason(sp.place()),
                        sp.distanceKm()
                ))
                .toList();
    }

    private String sanitizeJson(String raw) {
        String trimmed = raw.trim();

        if (trimmed.startsWith("```")) {
            trimmed = trimmed.replaceAll("^```(json)?\\s*", "");
            trimmed = trimmed.replaceAll("\\s*```$", "");
        }

        return trimmed.trim();
    }

    private String buildUserPrompt(
            List<ScoredPlace> candidates,
            RecommendationRequest request
    ) {
        StringBuilder sb = new StringBuilder();

        sb.append("사용자 설문 결과:\n");
        sb.append("- 목적: ").append(request.getPurpose()).append("\n");
        sb.append("- 컨디션: ").append(request.getCondition()).append("\n");
        sb.append("- 공부 시간: ").append(request.getStudyTime()).append("\n");
        sb.append("- 이동 가능 거리: ").append(request.getDistance()).append("\n\n");

        sb.append("후보 장소 목록 (이 중에서만 골라야 함):\n");

        for (ScoredPlace sp : candidates) {
            Place place = sp.place();
            sb.append(String.format(
                    "- id:%d, 이름:%s, 카테고리:%s, 소음:%s, 크기:%s, 집중도:%s, 분위기:%s, 콘센트:%s, 거리:%s%n",
                    place.getId(),
                    place.getName(),
                    place.getCategory(),
                    place.getNoiseLevel(),
                    place.getSizeLevel(),
                    place.getFocusLevel(),
                    place.getMood(),
                    Boolean.TRUE.equals(place.getHasOutlet()) ? "있음" : "없음",
                    sp.distanceKm() != null ? sp.distanceKm() + "km" : "정보 없음"
            ));
        }

        sb.append("\n위 후보 중에서 사용자에게 가장 잘 맞는 장소 3개를 골라 순서대로 추천하고, ");
        sb.append("각 장소마다 한국어로 친근하고 구체적인 추천 이유를 1~2문장으로 작성해줘.");

        return sb.toString();
    }

    private Double calculateDistanceKm(
            Place place,
            RecommendationRequest request
    ) {
        if (request.getUserLatitude() == null
                || request.getUserLongitude() == null
                || place.getLatitude() == null
                || place.getLongitude() == null) {
            return null;
        }

        double distance = DistanceCalculator.calculateDistanceKm(
                request.getUserLatitude(),
                request.getUserLongitude(),
                place.getLatitude(),
                place.getLongitude()
        );

        return Math.round(distance * 10) / 10.0;
    }

    private double resolveMaxDistanceKm(String distanceOption) {
        if (distanceOption == null) {
            return Double.MAX_VALUE;
        }

        if (distanceOption.contains("10")) {
            return 1.0;
        }

        if (distanceOption.contains("30")) {
            return 3.0;
        }

        if (distanceOption.contains("1시간")) {
            return 6.0;
        }

        return Double.MAX_VALUE;
    }

    private int calculateScore(
            Place place,
            RecommendationRequest request,
            Double distanceKm
    ) {
        int score = 0;

        if (request.getPurpose() != null) {
            if (request.getPurpose().contains("시험")
                    && place.getNoiseLevel() == NoiseLevel.LOW) {
                score += 3;
            }

            if (request.getPurpose().contains("과제")
                    && Boolean.TRUE.equals(place.getHasOutlet())) {
                score += 3;
            }

            if (request.getPurpose().contains("팀플")
                    && place.getNoiseLevel() == NoiseLevel.MEDIUM) {
                score += 2;
            }

            if (request.getPurpose().contains("독서")
                    && place.getNoiseLevel() == NoiseLevel.LOW) {
                score += 2;
            }
        }

        if (request.getCondition() != null) {
            if (request.getCondition().contains("피곤")
                    && place.getMood() != null
                    && place.getMood().contains("편안")) {
                score += 2;
            }

            if (request.getCondition().contains("집중")
                    && place.getNoiseLevel() == NoiseLevel.LOW) {
                score += 2;
            }

            if (request.getCondition().contains("기분")
                    && place.getTheme() != null
                    && place.getTheme().contains("현실도피")) {
                score += 2;
            }

            if (request.getCondition().contains("조용")
                    && place.getNoiseLevel() == NoiseLevel.LOW) {
                score += 2;
            }
        }

        if (request.getStudyTime() != null) {
            if (request.getStudyTime().contains("3")
                    && Boolean.TRUE.equals(place.getHasOutlet())) {
                score += 2;
            }

            if (request.getStudyTime().contains("1")
                    && place.getSizeLevel() == SizeLevel.SMALL) {
                score += 1;
            }

            if (request.getStudyTime().contains("장시간")
                    && Boolean.TRUE.equals(place.getHasOutlet())) {
                score += 2;
            }
        }

        if (distanceKm != null) {
            double maxDistanceKm = resolveMaxDistanceKm(request.getDistance());

            if (distanceKm <= maxDistanceKm) {
                score += 3;
            }

            if (distanceKm <= 1.0
                    && Boolean.TRUE.equals(place.getHiddenSpot())) {
                score += 1;
            }
        } else if (request.getDistance() != null) {
            if (request.getDistance().contains("10")) {
                score += 1;
            }

            if (request.getDistance().contains("30")) {
                score += 1;
            }

            if (request.getDistance().contains("1시간")) {
                score += 1;
            }
        }

        if (place.getFocusLevel() == FocusLevel.HIGH) {
            score += 2;
        }

        if (place.getSizeLevel() == SizeLevel.LARGE) {
            score += 1;
        }

        return score;
    }

    private String buildBaseReason(Place place) {
        if (place.getFocusLevel() == FocusLevel.HIGH
                && Boolean.TRUE.equals(place.getHasOutlet())) {
            return "집중도가 높고 콘센트가 있어 장시간 공부하기 좋아요.";
        }

        if (place.getNoiseLevel() == NoiseLevel.LOW) {
            return "소음도가 낮아 집중이 필요한 공부에 적합해요.";
        }

        if (Boolean.TRUE.equals(place.getHasOutlet())) {
            return "콘센트가 있어 노트북 과제나 장시간 공부에 적합해요.";
        }

        return "입력한 조건과 전반적으로 잘 맞는 장소예요.";
    }

    private record ScoredPlace(Place place, int score, Double distanceKm) {
    }
}