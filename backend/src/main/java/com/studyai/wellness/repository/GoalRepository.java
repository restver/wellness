package com.studyai.wellness.repository;

import com.studyai.wellness.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Goal entity.
 *
 * <p>This repository provides data access methods for Goal entities.</p>
 */
@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

    /**
     * Find all active goals for a specific user.
     *
     * @param userId the user ID
     * @return list of active goals
     */
    List<Goal> findByUserIdAndActiveTrueOrderByDeadlineAsc(Long userId);

    /**
     * Find achieved goals for a specific user.
     *
     * @param userId the user ID
     * @return list of achieved goals
     */
    List<Goal> findByUserIdAndAchievedTrue(Long userId);
}
