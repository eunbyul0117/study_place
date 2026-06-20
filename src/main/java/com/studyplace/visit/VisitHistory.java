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

    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    @Enumerated(EnumType.STRING)
    private NoiseStatus noiseStatus;

    public VisitHistory(
            Long userId,
            Long placeId,
            LocalDateTime visitedAt,
            SeatStatus seatStatus,
            NoiseStatus noiseStatus
    ) {
        this.userId = userId;
        this.placeId = placeId;
        this.visitedAt = visitedAt;
        this.seatStatus = seatStatus;
        this.noiseStatus = noiseStatus;
    }
}