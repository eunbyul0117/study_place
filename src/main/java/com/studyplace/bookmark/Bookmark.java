package com.studyplace.bookmark;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long placeId;

    public Bookmark(
            Long userId,
            Long placeId
    ) {
        this.userId = userId;
        this.placeId = placeId;
    }
}