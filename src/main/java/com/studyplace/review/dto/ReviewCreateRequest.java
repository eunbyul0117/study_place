package com.studyplace.review.dto;

import com.studyplace.place.NoiseLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewCreateRequest {

    private Long placeId;

    private Long userId;

    private Integer rating;

    private NoiseLevel noiseLevel;

    private String content;
}