package com.studyplace.visit.dto;

import com.studyplace.visit.NoiseStatus;
import com.studyplace.visit.SeatStatus;
import com.studyplace.visit.VisitHistory;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VisitHistoryResponse {

    private Long id;

    private Long userId;

    private Long placeId;

    private LocalDateTime visitedAt;

    private SeatStatus seatStatus;

    private NoiseStatus noiseStatus;

    public VisitHistoryResponse(
            VisitHistory visitHistory
    ) {
        this.id = visitHistory.getId();
        this.userId = visitHistory.getUserId();
        this.placeId = visitHistory.getPlaceId();
        this.visitedAt = visitHistory.getVisitedAt();
        this.seatStatus = visitHistory.getSeatStatus();
        this.noiseStatus = visitHistory.getNoiseStatus();
    }
}