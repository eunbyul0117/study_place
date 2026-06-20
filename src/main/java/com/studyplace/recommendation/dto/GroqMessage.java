package com.studyplace.recommendation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroqMessage {

    private String role;
    private String content;

    public GroqMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }
}