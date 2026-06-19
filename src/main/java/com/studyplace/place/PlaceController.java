package com.studyplace.place;

import com.studyplace.place.dto.PlaceCreateRequest;
import com.studyplace.place.dto.PlaceResponse;
import com.studyplace.place.dto.PlaceUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping
    public PlaceResponse createPlace(
            @RequestBody PlaceCreateRequest request
    ) {
        return placeService.createPlace(request);
    }

    @GetMapping
    public List<PlaceResponse> getPlaces() {
        return placeService.getPlaces();
    }

    @GetMapping("/search")
    public List<PlaceResponse> searchPlaces(
            @RequestParam String keyword
    ) {
        return placeService.searchPlaces(keyword);
    }

    @GetMapping("/theme/{theme}")
    public List<PlaceResponse> getPlacesByTheme(
            @PathVariable String theme
    ) {
        return placeService.getPlacesByTheme(theme);
    }

    @GetMapping("/{placeId}")
    public PlaceResponse getPlace(
            @PathVariable Long placeId
    ) {
        return placeService.getPlace(placeId);
    }

    @PutMapping("/{placeId}")
    public PlaceResponse updatePlace(
            @PathVariable Long placeId,
            @RequestBody PlaceUpdateRequest request
    ) {
        return placeService.updatePlace(placeId, request);
    }

    @DeleteMapping("/{placeId}")
    public void deletePlace(
            @PathVariable Long placeId
    ) {
        placeService.deletePlace(placeId);
    }
}