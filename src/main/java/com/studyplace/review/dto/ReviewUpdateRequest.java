package com.studyplace.review.dto;

import com.studyplace.place.NoiseLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewUpdateRequest {

    private Integer rating;

    private NoiseLevel noiseLevel;

    private String content;
}