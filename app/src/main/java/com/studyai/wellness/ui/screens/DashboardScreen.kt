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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
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
import com.studyai.wellness.ui.components.AppMetricCard
import com.studyai.wellness.ui.components.AppStatusBar
import com.studyai.wellness.ui.components.FullScreenLoading
import com.studyai.wellness.ui.theme.Background
import com.studyai.wellness.ui.theme.PrimaryGreen
import com.studyai.wellness.ui.theme.TextPrimary
import com.studyai.wellness.ui.theme.TextSecondary
import com.studyai.wellness.viewmodels.DashboardViewModel
import com.studyai.wellness.viewmodels.DashboardUiState

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onNavigateToProfile: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToCalendar: () -> Unit = {},
    onNavigateToStats: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is DashboardUiState.Loading -> {
                FullScreenLoading()
            }
            is DashboardUiState.Success -> {
                DashboardContent(
                    dashboard = state.dashboard,
                    onNavigateToProfile = onNavigateToProfile,
                    onNavigateToSettings = onNavigateToSettings,
                    onNavigateToCalendar = onNavigateToCalendar,
                    onNavigateToStats = onNavigateToStats,
                    onNavigateToNotifications = onNavigateToNotifications,
                    onLogout = onLogout
                )
            }
            is DashboardUiState.Error -> {
                com.studyai.wellness.ui.components.ErrorMessage(
                    message = state.message,
                    onRetry = viewModel::loadDashboard
                )
            }
        }
    }
}

@Composable
private fun DashboardContent(
    dashboard: com.studyai.wellness.data.model.DashboardDto,
    onNavigateToProfile: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToCalendar: () -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        AppStatusBar()

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Hello, ${dashboard.user.name}",
                            color = TextPrimary,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Let's check your progress",
                            color = TextSecondary,
                            fontSize = 14.sp
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(PrimaryGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = dashboard.user.name.first().toString(),
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Metrics
            item {
                Text(
                    text = "Today's Progress",
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            items(dashboard.metrics.take(3)) { metric ->
                AppMetricCard(
                    title = metric.title,
                    value = metric.value,
                    subtitle = metric.subtitle
                )
            }

            // Habits
            item {
                Text(
                    text = "Daily Habits",
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            items(dashboard.habits) { habit ->
                HabitItem(habit = habit)
            }

            // Weekly Progress
            item {
                Text(
                    text = "This Week",
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                WeeklyProgressChart(dashboard.weeklyProgress.days)
            }
        }

        AppBottomTabBar(
            currentRoute = "dashboard",
            onTabSelected = { route ->
                when (route) {
                    "profile" -> onNavigateToProfile()
                    "settings" -> onNavigateToSettings()
                    "calendar" -> onNavigateToCalendar()
                    "stats" -> onNavigateToStats()
                    "notifications" -> onNavigateToNotifications()
                }
            }
        )
    }
}

@Composable
private fun HabitItem(habit: com.studyai.wellness.data.model.HabitDto) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = habit.icon,
                fontSize = 24.sp
            )
            Column {
                Text(
                    text = habit.name,
                    color = TextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${habit.streak} day streak",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
        }
        Icon(
            imageVector = if (habit.completed) Icons.Filled.CheckCircle else Icons.Outlined.Circle,
            contentDescription = null,
            tint = if (habit.completed) PrimaryGreen else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun WeeklyProgressChart(days: List<com.studyai.wellness.data.model.DayProgressDto>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.White, androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        days.forEach { day ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height((day.value * 60).dp)
                        .background(
                            if (day.completed) PrimaryGreen else Color(0xFFEDECEA),
                            androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                        )
                )
                Text(
                    text = day.day.take(1),
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
        }
    }
}
