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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.studyai.wellness.viewmodels.ProfileViewModel
import com.studyai.wellness.viewmodels.ProfileUiState

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateToDashboard: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToCalendar: () -> Unit = {},
    onNavigateToStats: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is ProfileUiState.Loading -> {
                com.studyai.wellness.ui.components.FullScreenLoading()
            }
            is ProfileUiState.Success -> {
                ProfileContent(
                    user = state.user,
                    onLogout = onLogout,
                    onNavigateToDashboard = onNavigateToDashboard,
                    onNavigateToSettings = onNavigateToSettings,
                    onNavigateToCalendar = onNavigateToCalendar,
                    onNavigateToStats = onNavigateToStats,
                    onNavigateToNotifications = onNavigateToNotifications
                )
            }
            is ProfileUiState.Error -> {
                com.studyai.wellness.ui.components.ErrorMessage(
                    message = state.message,
                    onRetry = viewModel::loadProfile
                )
            }
        }
    }
}

@Composable
private fun ProfileContent(
    user: com.studyai.wellness.data.model.UserDto,
    onLogout: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToCalendar: () -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToNotifications: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header
            Text(
                text = "My Profile",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Profile Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user.name.first().toString(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = user.name,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = user.email,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    fontSize = 14.sp
                )

                IconButton(
                    onClick = { /* TODO: Navigate to edit profile */ },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Profile",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Info Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProfileInfoItem(label = "Member Since", value = "January 2024")
                ProfileInfoItem(label = "Notifications", value = if (user.preferences.notificationsEnabled) "Enabled" else "Disabled")
                ProfileInfoItem(label = "Theme", value = if (user.preferences.darkMode) "Dark" else "Light")
            }

            Spacer(modifier = Modifier.weight(1f))

            AppPrimaryButton(
                text = "Log Out",
                onClick = onLogout
            )
        }

        AppBottomTabBar(
            currentRoute = "profile",
            onTabSelected = { route ->
                when (route) {
                    "dashboard" -> onNavigateToDashboard()
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
private fun ProfileInfoItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            fontSize = 14.sp
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
