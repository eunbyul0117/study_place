package com.studyplace.review.dto;

import com.studyplace.place.NoiseLevel;
import com.studyplace.review.Review;
import lombok.Getter;

@Getter
public class ReviewResponse {

    private Long id;

    private Long placeId;

    private Long userId;

    private Integer rating;

    private NoiseLevel noiseLevel;

    private String content;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.placeId = review.getPlaceId();
        this.userId = review.getUserId();
        this.rating = review.getRating();
        this.noiseLevel = review.getNoiseLevel();
        this.content = review.getContent();
    }
}