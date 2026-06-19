package com.studyplace.bookmark;

import com.studyplace.bookmark.dto.BookmarkCreateRequest;
import com.studyplace.bookmark.dto.BookmarkResponse;
import com.studyplace.bookmark.dto.BookmarkPlaceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    public BookmarkResponse createBookmark(
            @RequestBody BookmarkCreateRequest request
    ) {

        return bookmarkService.createBookmark(request);
    }

    @GetMapping("/user/{userId}")
    public List<BookmarkPlaceResponse> getBookmarkPlacesByUser(
            @PathVariable Long userId
    ) {
        return bookmarkService.getBookmarkPlacesByUser(userId);
    }

    @DeleteMapping("/{bookmarkId}")
    public void deleteBookmark(
            @PathVariable Long bookmarkId
    ) {

        bookmarkService.deleteBookmark(bookmarkId);
    }
}