package com.studyplace.visit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitHistoryRepository
        extends JpaRepository<VisitHistory, Long> {

    List<VisitHistory> findByPlaceId(Long placeId);

    List<VisitHistory> findByUserId(Long userId);
}