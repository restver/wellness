package com.studyai.wellness.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.studyai.wellness.ui.components.AppBottomTabBar
import com.studyai.wellness.ui.components.AppStatusBar
import com.studyai.wellness.ui.theme.Background
import com.studyai.wellness.ui.theme.PrimaryGreen
import com.studyai.wellness.ui.theme.TextPrimary
import com.studyai.wellness.ui.theme.TextSecondary
import com.studyai.wellness.viewmodels.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
    val darkModeEnabled by viewModel.darkModeEnabled.collectAsState()
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        AppStatusBar()

        // Header
        Text(
            text = "Settings",
            color = TextPrimary,
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = (-0.5).sp,
            modifier = Modifier.padding(20.dp, 24.dp, 16.dp, 24.dp)
        )

        LazyColumnWithFooter(
            modifier = Modifier.weight(1f),
            content = {
                // General section
                item {
                    Text(
                        text = "General",
                        color = TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                    )
                }

                item {
                    SettingSwitchItem(
                        title = "Notifications",
                        subtitle = "Enable push notifications",
                        checked = notificationsEnabled,
                        onCheckedChange = viewModel::toggleNotifications
                    )
                }

                item {
                    SettingSwitchItem(
                        title = "Dark Mode",
                        subtitle = "Use dark theme",
                        checked = darkModeEnabled,
                        onCheckedChange = viewModel::toggleDarkMode
                    )
                }

                item {
                    SettingMenuItem(
                        icon = "🌐",
                        title = "Language",
                        subtitle = selectedLanguage,
                        onClick = { /* Show language picker */ }
                    )
                }

                // Privacy section
                item {
                    Text(
                        text = "Privacy",
                        color = TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                    )
                }

                item {
                    SettingMenuItem(
                        icon = "🔒",
                        title = "Privacy Policy",
                        subtitle = "Read our privacy policy",
                        onClick = { }
                    )
                }

                item {
                    SettingMenuItem(
                        icon = "📊",
                        title = "Data Usage",
                        subtitle = "Manage your data",
                        onClick = { }
                    )
                }

                // Support section
                item {
                    Text(
                        text = "Support",
                        color = TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                    )
                }

                item {
                    SettingMenuItem(
                        icon = "💬",
                        title = "Help Center",
                        subtitle = "Get help and support",
                        onClick = { }
                    )
                }

                item {
                    SettingMenuItem(
                        icon = "📧",
                        title = "Contact Us",
                        subtitle = "Reach out to our team",
                        onClick = { }
                    )
                }

                // About section
                item {
                    Text(
                        text = "About",
                        color = TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                    )
                }

                item {
                    SettingMenuItem(
                        icon = "ℹ️",
                        title = "App Version",
                        subtitle = "Version 1.0.0",
                        onClick = { }
                    )
                }

                item {
                    SettingMenuItem(
                        icon = "📜",
                        title = "Terms of Service",
                        subtitle = "Read our terms",
                        onClick = { }
                    )
                }
            },
            footer = {
                AppBottomTabBar(
                    currentRoute = "settings",
                    onTabSelected = {}
                )
            }
        )
    }
}

@Composable
private fun SettingSwitchItem(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
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
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                color = TextSecondary,
                fontSize = 13.sp
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedTrackColor = PrimaryGreen,
                uncheckedTrackColor = Color(0xFFEDECEA)
            )
        )
    }
}

@Composable
private fun SettingMenuItem(
    icon: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = icon,
            fontSize = 24.sp
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = TextPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                color = TextSecondary,
                fontSize = 13.sp
            )
        }
        Text(
            text = ">",
            color = TextSecondary
        )
    }
}

@Composable
private fun LazyColumnWithFooter(
    modifier: Modifier = Modifier,
    content: androidx.compose.foundation.lazy.LazyListScope.() -> Unit,
    footer: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        LazyColumn(
            contentPadding = androidx.compose.foundation.layout.PaddingValues(24.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            content = content
        )
        footer()
    }
}
