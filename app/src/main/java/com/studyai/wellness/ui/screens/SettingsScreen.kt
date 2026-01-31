package com.studyai.wellness.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.studyai.wellness.ui.components.AppBottomTabBar
import com.studyai.wellness.ui.theme.Background
import com.studyai.wellness.ui.theme.PrimaryGreen
import com.studyai.wellness.ui.theme.TextPrimary
import com.studyai.wellness.ui.theme.TextSecondary
import com.studyai.wellness.viewmodels.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigateToDashboard: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToCalendar: () -> Unit = {},
    onNavigateToStats: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {}
) {
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
    val darkModeEnabled by viewModel.darkModeEnabled.collectAsState()
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
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
                text = "Settings",
                color = TextPrimary,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Notifications Section
            Text(
                text = "Notifications",
                color = TextSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            SettingItem(
                title = "Push Notifications",
                description = "Receive notifications on your device",
                enabled = notificationsEnabled,
                onToggle = viewModel::toggleNotifications
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Appearance Section
            Text(
                text = "Appearance",
                color = TextSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            SettingItem(
                title = "Dark Mode",
                description = "Use dark theme",
                enabled = darkModeEnabled,
                onToggle = viewModel::toggleDarkMode
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Language Section
            Text(
                text = "Language",
                color = TextSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            LanguageItem(
                currentLanguage = selectedLanguage,
                onSelectLanguage = viewModel::selectLanguage
            )
        }

        AppBottomTabBar(
            currentRoute = "settings",
            onTabSelected = { route ->
                when (route) {
                    "dashboard" -> onNavigateToDashboard()
                    "profile" -> onNavigateToProfile()
                    "calendar" -> onNavigateToCalendar()
                    "stats" -> onNavigateToStats()
                    "notifications" -> onNavigateToNotifications()
                }
            }
        )
    }
}

@Composable
private fun SettingItem(
    title: String,
    description: String,
    enabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = description,
                color = TextSecondary,
                fontSize = 13.sp
            )
        }
        Switch(
            checked = enabled,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = PrimaryGreen,
                checkedTrackColor = PrimaryGreen.copy(alpha = 0.5f),
                uncheckedThumbColor = Color.Gray,
                uncheckedTrackColor = Color.Gray.copy(alpha = 0.5f)
            )
        )
    }
}

@Composable
private fun LanguageItem(
    currentLanguage: String,
    onSelectLanguage: (String) -> Unit
) {
    val languages = listOf(
        "en" to "English",
        "es" to "Español",
        "fr" to "Français",
        "de" to "Deutsch",
        "zh" to "中文"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "App Language",
            color = TextPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        languages.forEach { (code, name) ->
            LanguageOption(
                name = name,
                selected = currentLanguage == code,
                onClick = { onSelectLanguage(code) }
            )
        }
    }
}

@Composable
private fun LanguageOption(
    name: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (selected) PrimaryGreen.copy(alpha = 0.1f) else Color.Transparent,
                RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            color = if (selected) PrimaryGreen else TextPrimary,
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
        )
        if (selected) {
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.Check,
                contentDescription = null,
                tint = PrimaryGreen
            )
        }
    }
}
