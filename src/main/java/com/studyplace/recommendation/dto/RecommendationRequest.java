package com.studyplace.recommendation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommendationRequest {

    private String purpose;      // 시험공부, 과제, 팀플, 독서
    private String condition;    // 피곤함, 집중필요, 기분전환
    private String studyTime;    // 1시간 이하, 1~3시간, 3시간 이상
    private String distance;     // 10분 이내, 30분 이내, 1시간 이내


    private Double userLatitude;
    private Double userLongitude;
}