package com.studyai.wellness.controller;

import com.studyai.wellness.dto.NotificationGroupDto;
import com.studyai.wellness.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for notification operations.
 *
 * <p>This controller handles endpoints for retrieving and managing
 * user notifications.</p>
 */
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * Get all notifications for the current user, grouped by date.
     *
     * @param userId the ID of the user from JWT token
     * @return list of NotificationGroupDto
     */
    @GetMapping
    public ResponseEntity<List<NotificationGroupDto>> getNotifications(@RequestParam Long userId) {
        log.info("Fetching notifications for user: {}", userId);
        List<NotificationGroupDto> notifications = notificationService.getNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Mark a specific notification as read.
     *
     * @param id the ID of the notification
     * @return 204 No Content on success
     */
    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        log.info("Marking notification as read: {}", id);
        notificationService.markAsRead(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Mark all notifications as read for the current user.
     *
     * @param userId the ID of the user from JWT token
     * @return 204 No Content on success
     */
    @PutMapping("/read-all")
    public ResponseEntity<Void> markAllAsRead(@RequestParam Long userId) {
        log.info("Marking all notifications as read for user: {}", userId);
        notificationService.markAllAsRead(userId);
        return ResponseEntity.noContent().build();
    }
}
