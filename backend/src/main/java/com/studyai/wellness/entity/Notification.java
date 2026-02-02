package com.studyai.wellness.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity class representing a user notification.
 *
 * <p>This entity stores notifications sent to users such as
 * reminders, achievements, updates, and alerts.</p>
 */
@Entity
@Table(name = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who receives this notification.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Title of the notification.
     */
    @Column(nullable = false, length = 200)
    private String title;

    /**
     * Detailed message content.
     */
    @Column(nullable = false, length = 1000)
    private String message;

    /**
     * Type of the notification.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationType type;

    /**
     * Flag indicating if the notification has been read.
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean read = false;

    /**
     * Timestamp when the notification was created.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    /**
     * Enumeration of notification types.
     */
    public enum NotificationType {
        /**
         * Reminder notification.
         */
        REMINDER,

        /**
         * Achievement unlocked notification.
         */
        ACHIEVEMENT,

        /**
         * Progress update notification.
         */
        UPDATE,

        /**
         * Alert or warning notification.
         */
        ALERT
    }
}
