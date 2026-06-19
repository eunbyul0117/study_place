package com.studyplace.review;

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

    private String noiseLevel;

    private String content;

    public Review(
            Long placeId,
            Long userId,
            Integer rating,
            String noiseLevel,
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
            String noiseLevel,
            String content
    ) {
        this.rating = rating;
        this.noiseLevel = noiseLevel;
        this.content = content;
    }
}