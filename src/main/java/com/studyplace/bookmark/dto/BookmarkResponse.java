package com.studyplace.bookmark.dto;

import com.studyplace.bookmark.Bookmark;
import lombok.Getter;

@Getter
public class BookmarkResponse {

    private Long id;

    private Long userId;

    private Long placeId;

    public BookmarkResponse(
            Bookmark bookmark
    ) {
        this.id = bookmark.getId();
        this.userId = bookmark.getUserId();
        this.placeId = bookmark.getPlaceId();
    }
}