package com.studyai.wellness.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for Notification Group.
 *
 * <p>This DTO groups notifications by date for display purposes.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationGroupDto {

    /**
     * Date label for the group (e.g., "Today", "Yesterday").
     */
    private String date;

    /**
     * List of notifications for this date.
     */
    private List<NotificationDto> notifications;
}
