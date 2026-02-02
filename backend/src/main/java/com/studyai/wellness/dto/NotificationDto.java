package com.studyai.wellness.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Notification information.
 *
 * <p>This DTO represents a user notification.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

    /**
     * Unique identifier of the notification.
     */
    private String id;

    /**
     * Title of the notification.
     */
    private String title;

    /**
     * Detailed message content.
     */
    private String message;

    /**
     * Type of the notification.
     */
    private NotificationType type;

    /**
     * Flag indicating if the notification is read.
     */
    @Builder.Default
    private Boolean read = false;

    /**
     * Timestamp when the notification was created.
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createdAt;

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
