package com.studyplace.recommendation;

import com.studyplace.recommendation.dto.RecommendationRequest;
import com.studyplace.recommendation.dto.RecommendationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/SPOTYU/recommendations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final GroqClient groqClient;

    @PostMapping
    public List<RecommendationResponse> recommendPlaces(
            @RequestBody RecommendationRequest request
    ) {
        return recommendationService.recommendPlaces(request);
    }

}