package com.studyai.wellness.repository;

import com.studyai.wellness.entity.WeeklyProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Repository interface for WeeklyProgress entity.
 *
 * <p>This repository provides data access methods for WeeklyProgress entities.</p>
 */
@Repository
public interface WeeklyProgressRepository extends JpaRepository<WeeklyProgress, Long> {

    /**
     * Find weekly progress by user ID and week start date.
     *
     * @param userId the user ID
     * @param weekStartDate the start date of the week
     * @return Optional containing the weekly progress if found
     */
    Optional<WeeklyProgress> findByUserIdAndWeekStartDate(Long userId, LocalDate weekStartDate);
}
