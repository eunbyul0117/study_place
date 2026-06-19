package com.studyplace.visit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VisitHistoryCreateRequest {

    private Long userId;

    private Long placeId;

    private String visitedAt;    // 예: 2026-09-09T11:40:00

    private String seatStatus;

    private String noiseStatus;
}