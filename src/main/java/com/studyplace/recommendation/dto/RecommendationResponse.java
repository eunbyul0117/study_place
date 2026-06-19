package com.studyplace.recommendation.dto;

import com.studyplace.place.Place;
import lombok.Getter;

@Getter
public class RecommendationResponse {

    private Long placeId;

    private String name;
    private String address;

    private Double latitude;
    private Double longitude;

    private String category;
    private String theme;

    private String description;
    private String imageUrl;

    private Boolean hasOutlet;

    private String noiseLevel;
    private String sizeLevel;
    private String focusLevel;

    private String mood;

    private String operatingHours;

    private Boolean hiddenSpot;

    private String reason;

    public RecommendationResponse(
            Place place,
            String reason
    ) {
        this.placeId = place.getId();

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

        this.reason = reason;
    }
}