package com.studyai.wellness.repository;

import com.studyai.wellness.entity.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Achievement entity.
 *
 * <p>This repository provides data access methods for Achievement entities.</p>
 */
@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    /**
     * Find all achievements for a specific user.
     *
     * @param userId the user ID
     * @return list of achievements
     */
    List<Achievement> findByUserIdOrderByUnlockedAtDesc(Long userId);

    /**
     * Find unlocked achievements for a specific user.
     *
     * @param userId the user ID
     * @return list of unlocked achievements
     */
    List<Achievement> findByUserIdAndUnlockedTrueOrderByUnlockedAtDesc(Long userId);
}
