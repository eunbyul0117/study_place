package com.studyplace.place;

import com.studyplace.place.dto.PlaceCreateRequest;
import com.studyplace.place.dto.PlaceResponse;
import com.studyplace.place.dto.PlaceUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

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
                request.getOpenTime(),
                request.getCloseTime(),
                request.getHiddenSpot()
        );

        Place savedPlace = placeRepository.save(place);

        return new PlaceResponse(savedPlace);
    }

    public List<PlaceResponse> getPlaces() {

        return placeRepository.findAll()
                .stream()
                .map(PlaceResponse::new)
                .toList();
    }

    public PlaceResponse getPlace(Long placeId) {

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() ->
                        new IllegalArgumentException("장소를 찾을 수 없습니다.")
                );

        return new PlaceResponse(place);
    }

    public PlaceResponse updatePlace(
            Long placeId,
            PlaceUpdateRequest request
    ) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() ->
                        new IllegalArgumentException("장소를 찾을 수 없습니다.")
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
                request.getOpenTime(),
                request.getCloseTime(),
                request.getHiddenSpot()
        );

        Place updatedPlace = placeRepository.save(place);

        return new PlaceResponse(updatedPlace);
    }

    public void deletePlace(Long placeId) {

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() ->
                        new IllegalArgumentException("장소를 찾을 수 없습니다.")
                );

        placeRepository.delete(place);
    }

    public List<PlaceResponse> getPlacesByTheme(String theme) {

        return placeRepository.findByTheme(theme)
                .stream()
                .map(PlaceResponse::new)
                .toList();
    }

    public List<PlaceResponse> searchPlaces(String keyword) {

        return placeRepository
                .findByNameContainingOrAddressContaining(
                        keyword,
                        keyword
                )
                .stream()
                .map(PlaceResponse::new)
                .toList();
    }
}