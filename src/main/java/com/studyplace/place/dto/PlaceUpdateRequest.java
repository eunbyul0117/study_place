package com.studyplace.place.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlaceUpdateRequest {

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

    private String openTime;
    private String closeTime;

    private Boolean hiddenSpot;
}