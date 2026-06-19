package com.studyplace.recommendation;

import com.studyplace.place.Place;
import com.studyplace.place.PlaceRepository;
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
                .sorted(
                        Comparator.comparingInt(place ->
                                calculateScore((Place) place, request)
                        ).reversed()
                )
                .limit(3)
                .map(place ->
                        new RecommendationResponse(
                                place,
                                createReason(place, request)
                        )
                )
                .toList();
    }

    private int calculateScore(
            Place place,
            RecommendationRequest request
    ) {
        int score = 0;

        if (request.getPurpose() != null) {
            if (request.getPurpose().contains("시험")
                    && "LOW".equals(place.getNoiseLevel())) {
                score += 3;
            }

            if (request.getPurpose().contains("과제")
                    && Boolean.TRUE.equals(place.getHasOutlet())) {
                score += 3;
            }

            if (request.getPurpose().contains("팀플")
                    && "MEDIUM".equals(place.getNoiseLevel())) {
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
                    && "LOW".equals(place.getNoiseLevel())) {
                score += 2;
            }

            if (request.getCondition().contains("기분")
                    && place.getTheme() != null
                    && place.getTheme().contains("현실도피")) {
                score += 2;
            }
        }

        if (request.getStudyTime() != null) {
            if (request.getStudyTime().contains("3")
                    && Boolean.TRUE.equals(place.getHasOutlet())) {
                score += 2;
            }
        }

        return score;
    }

    private String createReason(
            Place place,
            RecommendationRequest request
    ) {
        if ("LOW".equals(place.getNoiseLevel())
                && Boolean.TRUE.equals(place.getHasOutlet())) {
            return "조용하고 콘센트가 있어 오래 집중해서 공부하기 좋아요.";
        }

        if ("LOW".equals(place.getNoiseLevel())) {
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
}