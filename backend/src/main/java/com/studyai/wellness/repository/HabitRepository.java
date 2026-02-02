package com.studyai.wellness.repository;

import com.studyai.wellness.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Habit entity.
 *
 * <p>This repository provides data access methods for Habit entities.</p>
 */
@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {

    /**
     * Find all habits for a specific user.
     *
     * @param userId the user ID
     * @return list of habits for the user
     */
    List<Habit> findByUserIdAndActiveTrueOrderByDisplayOrderAsc(Long userId);

    /**
     * Find active habits for a specific user.
     *
     * @param userId the user ID
     * @return list of active habits
     */
    List<Habit> findByUserIdAndActiveTrue(Long userId);
}
