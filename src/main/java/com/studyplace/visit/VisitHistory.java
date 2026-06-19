package com.studyplace.visit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class VisitHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long placeId;

    private LocalDateTime visitedAt;

    private String seatStatus;   // 자리 많았어요, 적당했어요, 거의 없었어요

    private String noiseStatus;  // 매우 조용해요, 보통이에요, 시끄러워요

    public VisitHistory(
            Long userId,
            Long placeId,
            LocalDateTime visitedAt,
            String seatStatus,
            String noiseStatus
    ) {
        this.userId = userId;
        this.placeId = placeId;
        this.visitedAt = visitedAt;
        this.seatStatus = seatStatus;
        this.noiseStatus = noiseStatus;
    }
}