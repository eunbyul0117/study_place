package com.studyplace.review;

import com.studyplace.place.NoiseLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long placeId;

    private Long userId;

    private Integer rating;

    @Enumerated(EnumType.STRING)
    private NoiseLevel noiseLevel;

    private String content;

    public Review(
            Long placeId,
            Long userId,
            Integer rating,
            NoiseLevel noiseLevel,
            String content
    ) {
        this.placeId = placeId;
        this.userId = userId;
        this.rating = rating;
        this.noiseLevel = noiseLevel;
        this.content = content;
    }

    public void update(
            Integer rating,
            NoiseLevel noiseLevel,
            String content
    ) {
        this.rating = rating;
        this.noiseLevel = noiseLevel;
        this.content = content;
    }
}