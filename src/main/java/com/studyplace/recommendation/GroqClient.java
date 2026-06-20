package com.studyplace.recommendation;

import com.studyplace.recommendation.dto.GroqChatRequest;
import com.studyplace.recommendation.dto.GroqChatResponse;
import com.studyplace.recommendation.dto.GroqMessage;
import com.studyplace.recommendation.dto.GroqResponseFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class GroqClient {

    private final RestClient restClient;
    private final String model;

    public GroqClient(
            @Value("${groq.api-key}") String apiKey,
            @Value("${groq.base-url}") String baseUrl,
            @Value("${groq.model}") String model
    ) {
        this.model = model;
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public String chat(String systemPrompt, String userPrompt) {
        return chat(systemPrompt, userPrompt, false);
    }

    public String chat(String systemPrompt, String userPrompt, boolean jsonMode) {

        GroqChatRequest request = new GroqChatRequest(
                model,
                List.of(
                        new GroqMessage("system", systemPrompt),
                        new GroqMessage("user", userPrompt)
                ),
                jsonMode ? new GroqResponseFormat("json_object") : null
        );

        GroqChatResponse response = restClient.post()
                .uri("/chat/completions")
                .body(request)
                .retrieve()
                .body(GroqChatResponse.class);

        if (response == null
                || response.getChoices() == null
                || response.getChoices().isEmpty()) {
            throw new IllegalStateException("Groq 응답이 비어있습니다.");
        }

        return response.getChoices().get(0).getMessage().getContent();
    }
}