package com.studyplace.place.dto;

import com.studyplace.place.Category;
import com.studyplace.place.FocusLevel;
import com.studyplace.place.NoiseLevel;
import com.studyplace.place.Place;
import com.studyplace.place.SizeLevel;
import lombok.Getter;

@Getter
public class PlaceResponse {

    private Long id;

    private String name;
    private String address;

    private Double latitude;
    private Double longitude;

    private Category category;
    private String theme;

    private String description;
    private String imageUrl;

    private Boolean hasOutlet;

    private NoiseLevel noiseLevel;
    private SizeLevel sizeLevel;
    private FocusLevel focusLevel;

    private String mood;

    private String operatingHours;

    private Boolean hiddenSpot;

    private Double averageRating;
    private Long reviewCount;

    public PlaceResponse(
            Place place,
            Double averageRating,
            Long reviewCount
    ) {
        this.id = place.getId();
        this.name = place.getName();
        this.address = place.getAddress();
        this.latitude = place.getLatitude();
        this.longitude = place.getLongitude();
        this.category = place.getCategory();
        this.theme = place.getTheme();
        this.description = place.getDescription();
        this.imageUrl = place.getImageUrl();
        this.hasOutlet = place.getHasOutlet();
        this.noiseLevel = place.getNoiseLevel();
        this.sizeLevel = place.getSizeLevel();
        this.focusLevel = place.getFocusLevel();
        this.mood = place.getMood();
        this.operatingHours = place.getOperatingHours();
        this.hiddenSpot = place.getHiddenSpot();
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
    }

    public PlaceResponse(Place place) {
        this(place, 0.0, 0L);
    }
}