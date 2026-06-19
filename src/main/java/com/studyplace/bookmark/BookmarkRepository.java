package com.studyplace.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository
        extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByUserId(Long userId);

    boolean existsByUserIdAndPlaceId(
            Long userId,
            Long placeId
    );

    Optional<Bookmark> findByUserIdAndPlaceId(
            Long userId,
            Long placeId
    );
}