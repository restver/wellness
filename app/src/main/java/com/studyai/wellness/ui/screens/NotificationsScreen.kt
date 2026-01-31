package com.studyai.wellness.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.studyai.wellness.ui.components.AppBottomTabBar
import com.studyai.wellness.ui.components.AppStatusBar
import com.studyai.wellness.ui.components.AppTextButton
import com.studyai.wellness.ui.components.FullScreenLoading
import com.studyai.wellness.ui.theme.Background
import com.studyai.wellness.ui.theme.PrimaryGreen
import com.studyai.wellness.ui.theme.TextPrimary
import com.studyai.wellness.ui.theme.TextSecondary
import com.studyai.wellness.viewmodels.NotificationsViewModel
import com.studyai.wellness.viewmodels.NotificationsUiState

@Composable
fun NotificationsScreen(
    viewModel: NotificationsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is NotificationsUiState.Loading -> {
                FullScreenLoading()
            }
            is NotificationsUiState.Success -> {
                NotificationsContent(
                    notifications = state.notifications,
                    onMarkAsRead = viewModel::markAsRead,
                    onMarkAllAsRead = viewModel::markAllAsRead
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
    onMarkAllAsRead: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        AppStatusBar()

        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 24.dp, 16.dp, 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Notifications",
                color = TextPrimary,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-0.5).sp,
                modifier = Modifier.weight(1f)
            )
            AppTextButton(
                text = "Mark all read",
                onClick = onMarkAllAsRead
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            notifications.forEach { group ->
                item {
                    Text(
                        text = group.date,
                        color = TextSecondary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(group.notifications) { notification ->
                    NotificationItem(
                        notification = notification,
                        onClick = { onMarkAsRead(notification.id) }
                    )
                }
            }
        }

        AppBottomTabBar(
            currentRoute = "notifications",
            onTabSelected = {}
        )
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
                if (notification.read) Color.Transparent else PrimaryGreen.copy(alpha = 0.1f),
                RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Icon
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(
                    when (notification.type) {
                        com.studyai.wellness.data.model.NotificationType.REMINDER -> Color(0xFF6366F1)
                        com.studyai.wellness.data.model.NotificationType.ACHIEVEMENT -> Color(0xFFF59E0B)
                        com.studyai.wellness.data.model.NotificationType.UPDATE -> Color(0xFF10B981)
                        com.studyai.wellness.data.model.NotificationType.ALERT -> Color(0xFFEF4444)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = when (notification.type) {
                    com.studyai.wellness.data.model.NotificationType.REMINDER -> "⏰"
                    com.studyai.wellness.data.model.NotificationType.ACHIEVEMENT -> "🏆"
                    com.studyai.wellness.data.model.NotificationType.UPDATE -> "📢"
                    com.studyai.wellness.data.model.NotificationType.ALERT -> "⚠️"
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
                fontWeight = if (notification.read) FontWeight.Normal else FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = notification.message,
                color = TextSecondary,
                fontSize = 13.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
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
