package com.studyai.wellness.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.studyai.wellness.ui.components.AppBottomTabBar
import com.studyai.wellness.ui.components.AppPrimaryButton
import com.studyai.wellness.ui.theme.Background
import com.studyai.wellness.ui.theme.PrimaryGreen
import com.studyai.wellness.ui.theme.TextPrimary
import com.studyai.wellness.ui.theme.TextSecondary
import com.studyai.wellness.viewmodels.NotificationsViewModel
import com.studyai.wellness.viewmodels.NotificationsUiState

@Composable
fun NotificationsScreen(
    viewModel: NotificationsViewModel = hiltViewModel(),
    onNavigateToDashboard: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToCalendar: () -> Unit = {},
    onNavigateToStats: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is NotificationsUiState.Loading -> {
                com.studyai.wellness.ui.components.FullScreenLoading()
            }
            is NotificationsUiState.Success -> {
                NotificationsContent(
                    notifications = state.notifications,
                    onMarkAsRead = viewModel::markAsRead,
                    onMarkAllAsRead = viewModel::markAllAsRead,
                    onNavigateToDashboard = onNavigateToDashboard,
                    onNavigateToSettings = onNavigateToSettings,
                    onNavigateToProfile = onNavigateToProfile,
                    onNavigateToCalendar = onNavigateToCalendar,
                    onNavigateToStats = onNavigateToStats
                )
            }
            is NotificationsUiState.Error -> {
                com.studyai.wellness.ui.components.ErrorMessage(
                    message = state.message,
                    onRetry = viewModel::loadNotifications
                )
            }
        }
    }
}

@Composable
private fun NotificationsContent(
    notifications: List<com.studyai.wellness.data.model.NotificationGroupDto>,
    onMarkAsRead: (String) -> Unit,
    onMarkAllAsRead: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToCalendar: () -> Unit,
    onNavigateToStats: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Notifications",
                    color = TextPrimary,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Mark all as read button
            if (notifications.any { it.notifications.any { !it.read } }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    AppPrimaryButton(
                        text = "Mark All as Read",
                        onClick = onMarkAllAsRead,
                        modifier = Modifier.height(36.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Notifications List
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                notifications.forEach { group ->
                    item {
                        NotificationGroup(
                            group = group,
                            onMarkAsRead = onMarkAsRead
                        )
                    }
                }
            }
        }

        AppBottomTabBar(
            currentRoute = "notifications",
            onTabSelected = { route ->
                when (route) {
                    "dashboard" -> onNavigateToDashboard()
                    "settings" -> onNavigateToSettings()
                    "profile" -> onNavigateToProfile()
                    "calendar" -> onNavigateToCalendar()
                    "stats" -> onNavigateToStats()
                }
            }
        )
    }
}

@Composable
private fun NotificationGroup(
    group: com.studyai.wellness.data.model.NotificationGroupDto,
    onMarkAsRead: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Group Header
        if (group.date.isNotEmpty()) {
            Text(
                text = group.date,
                color = TextSecondary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // Notifications in this group
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            group.notifications.forEach { notification ->
                NotificationItem(
                    notification = notification,
                    onClick = { onMarkAsRead(notification.id) }
                )
            }
        }
    }
}

@Composable
private fun NotificationItem(
    notification: com.studyai.wellness.data.model.NotificationDto,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (notification.read) Color.White else PrimaryGreen.copy(alpha = 0.1f),
                RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(
                    when (notification.type) {
                        com.studyai.wellness.data.model.NotificationType.ACHIEVEMENT -> Color(0xFFFFD700)
                        com.studyai.wellness.data.model.NotificationType.REMINDER -> Color(0xFF87CEEB)
                        com.studyai.wellness.data.model.NotificationType.ALERT -> Color(0xFFFF6B6B)
                        com.studyai.wellness.data.model.NotificationType.UPDATE -> Color(0xFF87CEEB)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = when (notification.type) {
                    com.studyai.wellness.data.model.NotificationType.ACHIEVEMENT -> "🏆"
                    com.studyai.wellness.data.model.NotificationType.REMINDER -> "⏰"
                    com.studyai.wellness.data.model.NotificationType.ALERT -> "⚠️"
                    com.studyai.wellness.data.model.NotificationType.UPDATE -> "📢"
                },
                fontSize = 20.sp
            )
        }

        // Content
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = notification.title,
                color = TextPrimary,
                fontSize = 15.sp,
                fontWeight = if (notification.read) FontWeight.Normal else FontWeight.SemiBold
            )
            Text(
                text = notification.message,
                color = TextSecondary,
                fontSize = 13.sp
            )
            Text(
                text = notification.createdAt,
                color = TextSecondary.copy(alpha = 0.7f),
                fontSize = 11.sp
            )
        }

        // Unread indicator
        if (!notification.read) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(PrimaryGreen)
            )
        }
    }
}
