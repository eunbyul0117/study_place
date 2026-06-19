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

    public Place(
            String name,
            String address,
            Double latitude,
            Double longitude,
            String category,
            String theme,
            String description,
            String imageUrl,
            Boolean hasOutlet,
            String noiseLevel,
            String sizeLevel,
            String focusLevel,
            String mood,
            String openTime,
            String closeTime,
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
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.hiddenSpot = hiddenSpot;
    }

    public void update(
            String name,
            String address,
            Double latitude,
            Double longitude,
            String category,
            String theme,
            String description,
            String imageUrl,
            Boolean hasOutlet,
            String noiseLevel,
            String sizeLevel,
            String focusLevel,
            String mood,
            String openTime,
            String closeTime,
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
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.hiddenSpot = hiddenSpot;
    }
}