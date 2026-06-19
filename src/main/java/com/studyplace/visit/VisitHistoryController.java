package com.studyplace.visit;

import com.studyplace.visit.dto.VisitHistoryCreateRequest;
import com.studyplace.visit.dto.VisitHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visit-histories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VisitHistoryController {

    private final VisitHistoryService visitHistoryService;

    @PostMapping
    public VisitHistoryResponse createVisitHistory(
            @RequestBody VisitHistoryCreateRequest request
    ) {
        return visitHistoryService.createVisitHistory(request);
    }

    @GetMapping("/place/{placeId}")
    public List<VisitHistoryResponse> getVisitHistoriesByPlace(
            @PathVariable Long placeId
    ) {
        return visitHistoryService.getVisitHistoriesByPlace(placeId);
    }

    @GetMapping("/user/{userId}")
    public List<VisitHistoryResponse> getVisitHistoriesByUser(
            @PathVariable Long userId
    ) {
        return visitHistoryService.getVisitHistoriesByUser(userId);
    }
}