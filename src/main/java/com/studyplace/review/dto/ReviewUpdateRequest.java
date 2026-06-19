package com.studyplace.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewUpdateRequest {

    private Integer rating;

    private String noiseLevel;

    private String content;
}