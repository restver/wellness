package com.studyai.wellness.service;

import com.studyai.wellness.dto.NotificationDto;
import com.studyai.wellness.dto.NotificationGroupDto;
import com.studyai.wellness.entity.Notification;
import com.studyai.wellness.entity.User;
import com.studyai.wellness.repository.NotificationRepository;
import com.studyai.wellness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling notification operations.
 *
 * <p>This service provides notification management functionality including
 * retrieving, creating, and marking notifications as read.</p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    /**
     * Get all notifications for a user, grouped by date.
     *
     * @param userId the ID of the user
     * @return list of NotificationGroupDto
     */
    @Transactional(readOnly = true)
    public List<NotificationGroupDto> getNotifications(Long userId) {
        log.info("Fetching notifications for user: {}", userId);

        List<Notification> notifications = notificationRepository
                .findByUserIdOrderByCreatedAtDesc(userId);

        return groupNotificationsByDate(notifications);
    }

    /**
     * Mark a specific notification as read.
     *
     * @param notificationId the ID of the notification
     */
    @Transactional
    public void markAsRead(Long notificationId) {
        log.info("Marking notification as read: {}", notificationId);

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setRead(true);
        notificationRepository.save(notification);
    }

    /**
     * Mark all notifications as read for a user.
     *
     * @param userId the ID of the user
     */
    @Transactional
    public void markAllAsRead(Long userId) {
        log.info("Marking all notifications as read for user: {}", userId);

        List<Notification> unreadNotifications = notificationRepository
                .findByUserIdAndReadFalseOrderByCreatedAtDesc(userId);

        unreadNotifications.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(unreadNotifications);
    }

    /**
     * Create a new notification for a user.
     *
     * @param user the user to receive the notification
     * @param title the notification title
     * @param message the notification message
     * @param type the notification type
     * @return the created notification
     */
    @Transactional
    public Notification createNotification(User user, String title, String message,
                                          Notification.NotificationType type) {
        Notification notification = Notification.builder()
                .user(user)
                .title(title)
                .message(message)
                .type(type)
                .read(false)
                .build();

        return notificationRepository.save(notification);
    }

    /**
     * Create a welcome notification for a new user.
     *
     * @param user the user to welcome
     */
    @Transactional
    public void createWelcomeNotification(User user) {
        createNotification(
                user,
                "Welcome to Wellness!",
                "Thanks for joining Wellness! Let's start your journey to a healthier lifestyle.",
                Notification.NotificationType.UPDATE
        );
    }

    /**
     * Create an achievement notification.
     *
     * @param user the user who achieved the milestone
     * @param achievementTitle the title of the achievement
     */
    @Transactional
    public void createAchievementNotification(User user, String achievementTitle) {
        createNotification(
                user,
                "Achievement Unlocked!",
                "Congratulations! You've earned: " + achievementTitle,
                Notification.NotificationType.ACHIEVEMENT
        );
    }

    /**
     * Group notifications by date for display.
     *
     * @param notifications list of notifications
     * @return list of NotificationGroupDto
     */
    private List<NotificationGroupDto> groupNotificationsByDate(List<Notification> notifications) {
        List<NotificationGroupDto> groups = new ArrayList<>();

        if (notifications.isEmpty()) {
            return groups;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);

        List<NotificationDto> todayNotifications = new ArrayList<>();
        List<NotificationDto> yesterdayNotifications = new ArrayList<>();
        List<NotificationDto> earlierNotifications = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDto dto = mapToNotificationDto(notification);

            if (notification.getCreatedAt().isAfter(yesterday)) {
                todayNotifications.add(dto);
            } else if (notification.getCreatedAt().isAfter(yesterday.minusDays(1))) {
                yesterdayNotifications.add(dto);
            } else {
                earlierNotifications.add(dto);
            }
        }

        if (!todayNotifications.isEmpty()) {
            groups.add(NotificationGroupDto.builder()
                    .date("Today")
                    .notifications(todayNotifications)
                    .build());
        }

        if (!yesterdayNotifications.isEmpty()) {
            groups.add(NotificationGroupDto.builder()
                    .date("Yesterday")
                    .notifications(yesterdayNotifications)
                    .build());
        }

        if (!earlierNotifications.isEmpty()) {
            groups.add(NotificationGroupDto.builder()
                    .date("Earlier")
                    .notifications(earlierNotifications)
                    .build());
        }

        return groups;
    }

    private NotificationDto mapToNotificationDto(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId().toString())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .type(mapToNotificationTypeDto(notification.getType()))
                .read(notification.getRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }

    private NotificationDto.NotificationType mapToNotificationTypeDto(Notification.NotificationType type) {
        return switch (type) {
            case REMINDER -> NotificationDto.NotificationType.REMINDER;
            case ACHIEVEMENT -> NotificationDto.NotificationType.ACHIEVEMENT;
            case UPDATE -> NotificationDto.NotificationType.UPDATE;
            case ALERT -> NotificationDto.NotificationType.ALERT;
        };
    }
}
