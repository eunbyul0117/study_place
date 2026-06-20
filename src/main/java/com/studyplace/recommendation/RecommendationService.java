package com.studyplace.recommendation;

import com.studyplace.place.FocusLevel;
import com.studyplace.place.NoiseLevel;
import com.studyplace.place.Place;
import com.studyplace.place.PlaceRepository;
import com.studyplace.place.SizeLevel;
import com.studyplace.recommendation.dto.RecommendationRequest;
import com.studyplace.recommendation.dto.RecommendationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final PlaceRepository placeRepository;

    public List<RecommendationResponse> recommendPlaces(
            RecommendationRequest request
    ) {
        return placeRepository.findAll()
                .stream()
                .map(place -> {
                    Double distanceKm = calculateDistanceKm(place, request);
                    int score = calculateScore(place, request, distanceKm);
                    String reason = createReason(place, request, distanceKm);
                    return new ScoredPlace(place, score, reason, distanceKm);
                })
                .sorted(
                        Comparator.comparingInt(ScoredPlace::score).reversed()
                )
                .limit(3)
                .map(sp -> new RecommendationResponse(
                        sp.place(),
                        sp.reason(),
                        sp.distanceKm()
                ))
                .toList();
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

            if (request.getDistance().contains("10")
                    && Boolean.TRUE.equals(place.getHiddenSpot())) {
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

    private String createReason(
            Place place,
            RecommendationRequest request,
            Double distanceKm
    ) {
        String baseReason = buildBaseReason(place);

        if (distanceKm != null) {
            return baseReason + String.format(" (현재 위치에서 약 %.1fkm)", distanceKm);
        }

        return baseReason;
    }

    private String buildBaseReason(Place place) {
        if (place.getFocusLevel() == FocusLevel.HIGH
                && Boolean.TRUE.equals(place.getHasOutlet())) {
            return "집중도가 높고 콘센트가 있어 장시간 공부하기 좋아요.";
        }

        if (place.getSizeLevel() == SizeLevel.LARGE
                && place.getNoiseLevel() == NoiseLevel.LOW) {
            return "공간이 넓고 조용해서 쾌적하게 공부하기 좋아요.";
        }

        if (place.getNoiseLevel() == NoiseLevel.LOW
                && Boolean.TRUE.equals(place.getHasOutlet())) {
            return "조용하고 콘센트가 있어 오래 집중해서 공부하기 좋아요.";
        }

        if (place.getNoiseLevel() == NoiseLevel.LOW) {
            return "소음도가 낮아 집중이 필요한 공부에 적합해요.";
        }

        if (Boolean.TRUE.equals(place.getHasOutlet())) {
            return "콘센트가 있어 노트북 과제나 장시간 공부에 적합해요.";
        }

        if (place.getTheme() != null) {
            return place.getTheme() + " 분위기에 잘 맞는 장소예요.";
        }

        return "입력한 조건과 전반적으로 잘 맞는 장소예요.";
    }

    private record ScoredPlace(
            Place place,
            int score,
            String reason,
            Double distanceKm
    ) {
    }
}