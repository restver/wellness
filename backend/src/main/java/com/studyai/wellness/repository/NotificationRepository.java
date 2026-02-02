package com.studyai.wellness.repository;

import com.studyai.wellness.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Notification entity.
 *
 * <p>This repository provides data access methods for Notification entities.</p>
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Find all notifications for a specific user, ordered by creation date.
     *
     * @param userId the user ID
     * @return list of notifications
     */
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    /**
     * Find unread notifications for a specific user.
     *
     * @param userId the user ID
     * @return list of unread notifications
     */
    List<Notification> findByUserIdAndReadFalseOrderByCreatedAtDesc(Long userId);

    /**
     * Count unread notifications for a specific user.
     *
     * @param userId the user ID
     * @return count of unread notifications
     */
    long countByUserIdAndReadFalse(Long userId);

    /**
     * Mark all notifications as read for a specific user.
     *
     * @param userId the user ID
     */
    @Query("UPDATE Notification n SET n.read = true WHERE n.user.id = :userId")
    void markAllAsReadByUserId(@Param("userId") Long userId);
}
