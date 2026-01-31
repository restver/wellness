package com.studyai.wellness.data.model

data class NotificationDto(
    val id: String,
    val title: String,
    val message: String,
    val type: NotificationType,
    val read: Boolean = false,
    val createdAt: String
)

enum class NotificationType {
    REMINDER,
    ACHIEVEMENT,
    UPDATE,
    ALERT
}

data class NotificationGroupDto(
    val date: String,
    val notifications: List<NotificationDto>
)
