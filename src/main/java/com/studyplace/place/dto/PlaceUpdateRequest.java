package com.studyplace.place.dto;

import com.studyplace.place.Category;
import com.studyplace.place.FocusLevel;
import com.studyplace.place.NoiseLevel;
import com.studyplace.place.SizeLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlaceUpdateRequest {

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
}