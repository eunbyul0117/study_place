package com.studyplace.recommendation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AiRecommendationResult {

    private List<AiRecommendationItem> recommendations;
}