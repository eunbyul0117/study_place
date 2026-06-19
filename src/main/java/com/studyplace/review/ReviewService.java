package com.studyplace.review;

import com.studyplace.global.NotFoundException;
import com.studyplace.review.dto.ReviewCreateRequest;
import com.studyplace.review.dto.ReviewResponse;
import com.studyplace.review.dto.ReviewUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewResponse createReview(
            ReviewCreateRequest request
    ) {

        Review review = new Review(
                request.getPlaceId(),
                request.getUserId(),
                request.getRating(),
                request.getNoiseLevel(),
                request.getContent()
        );

        Review savedReview =
                reviewRepository.save(review);

        return new ReviewResponse(savedReview);
    }

    public List<ReviewResponse> getReviewsByPlace(
            Long placeId
    ) {

        return reviewRepository.findByPlaceId(placeId)
                .stream()
                .map(ReviewResponse::new)
                .toList();
    }

    public ReviewResponse updateReview(
            Long reviewId,
            ReviewUpdateRequest request
    ) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new NotFoundException("리뷰를 찾을 수 없습니다.")
                );

        review.update(
                request.getRating(),
                request.getNoiseLevel(),
                request.getContent()
        );

        Review updatedReview = reviewRepository.save(review);

        return new ReviewResponse(updatedReview);
    }

    public void deleteReview(
            Long reviewId
    ) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new NotFoundException("리뷰를 찾을 수 없습니다.")
                );

        reviewRepository.delete(review);
    }
}