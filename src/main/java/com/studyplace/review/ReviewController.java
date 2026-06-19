package com.studyplace.review;

import com.studyplace.review.dto.ReviewCreateRequest;
import com.studyplace.review.dto.ReviewUpdateRequest;
import com.studyplace.review.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ReviewResponse createReview(
            @RequestBody ReviewCreateRequest request
    ) {

        return reviewService.createReview(request);
    }

    @PutMapping("/{reviewId}")
    public ReviewResponse updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewUpdateRequest request
    ) {
        return reviewService.updateReview(reviewId, request);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(
            @PathVariable Long reviewId
    ) {
        reviewService.deleteReview(reviewId);
    }

    @GetMapping("/place/{placeId}")
    public List<ReviewResponse> getReviewsByPlace(
            @PathVariable Long placeId
    ) {

        return reviewService.getReviewsByPlace(placeId);
    }
}