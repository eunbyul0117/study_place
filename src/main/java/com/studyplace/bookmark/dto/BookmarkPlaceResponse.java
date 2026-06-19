package com.studyplace.bookmark.dto;

import com.studyplace.bookmark.Bookmark;
import com.studyplace.place.Place;
import lombok.Getter;

@Getter
public class BookmarkPlaceResponse {

    private Long bookmarkId;

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

    private String mood;

    private String openTime;

    private String closeTime;

    private Boolean hiddenSpot;

    public BookmarkPlaceResponse(
            Bookmark bookmark,
            Place place
    ) {
        this.bookmarkId = bookmark.getId();

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
        this.mood = place.getMood();

        this.openTime = place.getOpenTime();
        this.closeTime = place.getCloseTime();

        this.hiddenSpot = place.getHiddenSpot();
    }
}