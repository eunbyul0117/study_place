package com.studyplace.visit;

import com.studyplace.visit.dto.VisitHistoryCreateRequest;
import com.studyplace.visit.dto.VisitHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitHistoryService {

    private final VisitHistoryRepository visitHistoryRepository;

    public VisitHistoryResponse createVisitHistory(
            VisitHistoryCreateRequest request
    ) {
        VisitHistory visitHistory = new VisitHistory(
                request.getUserId(),
                request.getPlaceId(),
                LocalDateTime.parse(request.getVisitedAt()),
                request.getSeatStatus(),
                request.getNoiseStatus()
        );

        VisitHistory savedVisitHistory =
                visitHistoryRepository.save(visitHistory);

        return new VisitHistoryResponse(savedVisitHistory);
    }

    public List<VisitHistoryResponse> getVisitHistoriesByPlace(
            Long placeId
    ) {
        return visitHistoryRepository.findByPlaceId(placeId)
                .stream()
                .map(VisitHistoryResponse::new)
                .toList();
    }

    public List<VisitHistoryResponse> getVisitHistoriesByUser(
            Long userId
    ) {
        return visitHistoryRepository.findByUserId(userId)
                .stream()
                .map(VisitHistoryResponse::new)
                .toList();
    }
}