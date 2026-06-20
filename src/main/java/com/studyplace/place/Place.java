package com.studyplace.place;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String theme;

    private String description;
    private String imageUrl;

    private Boolean hasOutlet;

    @Enumerated(EnumType.STRING)
    private NoiseLevel noiseLevel;

    @Enumerated(EnumType.STRING)
    private SizeLevel sizeLevel;

    @Enumerated(EnumType.STRING)
    private FocusLevel focusLevel;

    private String mood;

    private String operatingHours;

    private Boolean hiddenSpot;

    public Place(
            String name,
            String address,
            Double latitude,
            Double longitude,
            Category category,
            String theme,
            String description,
            String imageUrl,
            Boolean hasOutlet,
            NoiseLevel noiseLevel,
            SizeLevel sizeLevel,
            FocusLevel focusLevel,
            String mood,
            String operatingHours,
            Boolean hiddenSpot
    ) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.theme = theme;
        this.description = description;
        this.imageUrl = imageUrl;
        this.hasOutlet = hasOutlet;
        this.noiseLevel = noiseLevel;
        this.sizeLevel = sizeLevel;
        this.focusLevel = focusLevel;
        this.mood = mood;
        this.operatingHours = operatingHours;
        this.hiddenSpot = hiddenSpot;
    }

    public void update(
            String name,
            String address,
            Double latitude,
            Double longitude,
            Category category,
            String theme,
            String description,
            String imageUrl,
            Boolean hasOutlet,
            NoiseLevel noiseLevel,
            SizeLevel sizeLevel,
            FocusLevel focusLevel,
            String mood,
            String operatingHours,
            Boolean hiddenSpot
    ) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.theme = theme;
        this.description = description;
        this.imageUrl = imageUrl;
        this.hasOutlet = hasOutlet;
        this.noiseLevel = noiseLevel;
        this.sizeLevel = sizeLevel;
        this.focusLevel = focusLevel;
        this.mood = mood;
        this.operatingHours = operatingHours;
        this.hiddenSpot = hiddenSpot;
    }
}