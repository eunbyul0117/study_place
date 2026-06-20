package com.studyplace.recommendation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AiRecommendationItem {

    private Long placeId;
    private String reason;
}