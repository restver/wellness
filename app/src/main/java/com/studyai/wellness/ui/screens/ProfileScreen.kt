package com.studyai.wellness.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.studyai.wellness.ui.components.AppStatusBar
import com.studyai.wellness.ui.components.FullScreenLoading
import com.studyai.wellness.ui.theme.Background
import com.studyai.wellness.ui.theme.PrimaryGreen
import com.studyai.wellness.ui.theme.TextPrimary
import com.studyai.wellness.ui.theme.TextSecondary
import com.studyai.wellness.viewmodels.ProfileViewModel
import com.studyai.wellness.viewmodels.ProfileUiState

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is ProfileUiState.Loading -> {
                FullScreenLoading()
            }
            is ProfileUiState.Success -> {
                ProfileContent(
                    user = state.user,
                    onLogout = onLogout
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
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        AppStatusBar()

        LazyColumnWithFooter(
            modifier = Modifier.weight(1f),
            content = {
                // Header
                item {
                    Text(
                        text = "Profile",
                        color = TextPrimary,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = (-0.5).sp,
                        modifier = Modifier.padding(20.dp, 24.dp, 16.dp, 24.dp)
                    )
                }

                // Profile card
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(PrimaryGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = user.name.first().toString(),
                                color = Color.White,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = user.name,
                            color = TextPrimary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = user.email,
                            color = TextSecondary,
                            fontSize = 14.sp
                        )
                    }
                }

                // Account section
                item {
                    Text(
                        text = "Account",
                        color = TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                    )
                }

                item {
                    ProfileMenuItem(
                        icon = "👤",
                        title = "Personal Information",
                        subtitle = "Update your personal details"
                    )
                }

                item {
                    ProfileMenuItem(
                        icon = "🔒",
                        title = "Password",
                        subtitle = "Change your password"
                    )
                }

                // Preferences section
                item {
                    Text(
                        text = "Preferences",
                        color = TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                    )
                }

                item {
                    ProfileMenuItem(
                        icon = "🔔",
                        title = "Notifications",
                        subtitle = if (user.preferences.notificationsEnabled) "Enabled" else "Disabled"
                    )
                }

                item {
                    ProfileMenuItem(
                        icon = "🌙",
                        title = "Dark Mode",
                        subtitle = if (user.preferences.darkMode) "Enabled" else "Disabled"
                    )
                }

                item {
                    ProfileMenuItem(
                        icon = "🌐",
                        title = "Language",
                        subtitle = user.preferences.language
                    )
                }

                // Logout button
                item {
                    AppPrimaryButton(
                        text = "Log Out",
                        onClick = onLogout,
                        modifier = Modifier.padding(24.dp)
                    )
                }
            },
            footer = {
                AppBottomTabBar(
                    currentRoute = "profile",
                    onTabSelected = {}
                )
            }
        )
    }
}

@Composable
private fun ProfileMenuItem(
    icon: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
            .clickable(onClick = onClick),
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
        androidx.compose.foundation.lazy.LazyColumn(
            contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 100.dp),
            content = content
        )
        footer()
    }
}
