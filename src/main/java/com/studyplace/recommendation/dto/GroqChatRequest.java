package com.studyplace.recommendation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class GroqChatRequest {

    private String model;
    private List<GroqMessage> messages;

    @JsonProperty("response_format")
    private GroqResponseFormat responseFormat;

    public GroqChatRequest(
            String model,
            List<GroqMessage> messages,
            GroqResponseFormat responseFormat
    ) {
        this.model = model;
        this.messages = messages;
        this.responseFormat = responseFormat;
    }
}