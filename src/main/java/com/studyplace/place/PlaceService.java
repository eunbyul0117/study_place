package com.studyplace.place;

import com.studyplace.global.NotFoundException;
import com.studyplace.place.dto.PlaceCreateRequest;
import com.studyplace.place.dto.PlaceResponse;
import com.studyplace.place.dto.PlaceUpdateRequest;
import com.studyplace.review.Review;
import com.studyplace.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;

    public PlaceResponse createPlace(PlaceCreateRequest request) {

        Place place = new Place(
                request.getName(),
                request.getAddress(),
                request.getLatitude(),
                request.getLongitude(),
                request.getCategory(),
                request.getTheme(),
                request.getDescription(),
                request.getImageUrl(),
                request.getHasOutlet(),
                request.getNoiseLevel(),
                request.getSizeLevel(),
                request.getFocusLevel(),
                request.getMood(),
                request.getOperatingHours(),
                request.getHiddenSpot()
        );

        Place savedPlace = placeRepository.save(place);

        return toPlaceResponse(savedPlace);
    }

    public List<PlaceResponse> getPlaces() {

        return placeRepository.findAll()
                .stream()
                .map(this::toPlaceResponse)
                .toList();
    }

    public PlaceResponse getPlace(Long placeId) {

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() ->
                        new NotFoundException("장소를 찾을 수 없습니다.")
                );

        return toPlaceResponse(place);
    }

    public PlaceResponse updatePlace(
            Long placeId,
            PlaceUpdateRequest request
    ) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() ->
                        new NotFoundException("장소를 찾을 수 없습니다.")
                );

        place.update(
                request.getName(),
                request.getAddress(),
                request.getLatitude(),
                request.getLongitude(),
                request.getCategory(),
                request.getTheme(),
                request.getDescription(),
                request.getImageUrl(),
                request.getHasOutlet(),
                request.getNoiseLevel(),
                request.getSizeLevel(),
                request.getFocusLevel(),
                request.getMood(),
                request.getOperatingHours(),
                request.getHiddenSpot()
        );

        Place updatedPlace = placeRepository.save(place);

        return toPlaceResponse(updatedPlace);
    }

    public void deletePlace(Long placeId) {

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() ->
                        new NotFoundException("장소를 찾을 수 없습니다.")
                );

        placeRepository.delete(place);
    }

    public List<PlaceResponse> getPlacesByTheme(String theme) {

        if ("비밀섬".equals(theme)) {
            return placeRepository.findByHiddenSpotTrue()
                    .stream()
                    .map(this::toPlaceResponse)
                    .toList();
        }

        return placeRepository.findByTheme(theme)
                .stream()
                .map(this::toPlaceResponse)
                .toList();
    }

    public List<PlaceResponse> searchPlaces(String keyword) {

        return placeRepository
                .findByNameContainingOrAddressContaining(
                        keyword,
                        keyword
                )
                .stream()
                .map(this::toPlaceResponse)
                .toList();
    }

    private PlaceResponse toPlaceResponse(Place place) {

        List<Review> reviews = reviewRepository.findByPlaceId(place.getId());

        Long reviewCount = (long) reviews.size();

        Double averageRating = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        averageRating = Math.round(averageRating * 10) / 10.0;

        return new PlaceResponse(
                place,
                averageRating,
                reviewCount
        );
    }
}