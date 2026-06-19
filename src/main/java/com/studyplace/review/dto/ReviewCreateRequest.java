package com.studyplace.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewCreateRequest {

    private Long placeId;

    private Long userId;

    private Integer rating;

    private String noiseLevel;

    private String content;
}