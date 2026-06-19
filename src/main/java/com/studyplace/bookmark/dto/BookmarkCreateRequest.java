package com.studyplace.bookmark.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookmarkCreateRequest {

    private Long userId;

    private Long placeId;
}