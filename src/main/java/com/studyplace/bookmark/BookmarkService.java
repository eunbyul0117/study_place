package com.studyplace.bookmark;

import com.studyplace.bookmark.dto.BookmarkCreateRequest;
import com.studyplace.bookmark.dto.BookmarkPlaceResponse;
import com.studyplace.bookmark.dto.BookmarkResponse;
import com.studyplace.global.NotFoundException;
import com.studyplace.place.Place;
import com.studyplace.place.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PlaceRepository placeRepository;

    public BookmarkResponse createBookmark(
            BookmarkCreateRequest request
    ) {
        bookmarkRepository.findByUserIdAndPlaceId(
                request.getUserId(),
                request.getPlaceId()
        ).ifPresent(bookmark -> {
            throw new IllegalArgumentException("이미 저장한 장소입니다.");
        });

        Bookmark bookmark = new Bookmark(
                request.getUserId(),
                request.getPlaceId()
        );

        Bookmark savedBookmark =
                bookmarkRepository.save(bookmark);

        return new BookmarkResponse(savedBookmark);
    }

    public List<BookmarkPlaceResponse> getBookmarkPlacesByUser(
            Long userId
    ) {
        return bookmarkRepository.findByUserId(userId)
                .stream()
                .map(bookmark -> {
                    Place place = placeRepository.findById(bookmark.getPlaceId())
                            .orElseThrow(() ->
                                    new NotFoundException("장소를 찾을 수 없습니다.")
                            );

                    return new BookmarkPlaceResponse(bookmark, place);
                })
                .toList();
    }

    public void deleteBookmark(
            Long bookmarkId
    ) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() ->
                        new NotFoundException("북마크를 찾을 수 없습니다.")
                );

        bookmarkRepository.delete(bookmark);
    }
}