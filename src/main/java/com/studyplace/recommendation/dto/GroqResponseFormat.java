package com.studyplace.recommendation.dto;

import lombok.Getter;

@Getter
public class GroqResponseFormat {

    private String type;

    public GroqResponseFormat(String type) {
        this.type = type;
    }
}