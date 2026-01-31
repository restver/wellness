package com.studyai.wellness.navigation

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.navigation.NavHostController
import com.studyai.wellness.ui.components.AppMetricCard
import com.studyai.wellness.ui.theme.Background
import com.studyai.wellness.ui.theme.PrimaryGreen
import com.studyai.wellness.ui.theme.TextPrimary
import com.studyai.wellness.ui.theme.TextSecondary
import com.studyai.wellness.viewmodels.CalendarViewModel
import com.studyai.wellness.viewmodels.DashboardViewModel
import com.studyai.wellness.viewmodels.DashboardUiState
import com.studyai.wellness.viewmodels.ProfileViewModel
import com.studyai.wellness.viewmodels.ProfileUiState
import com.studyai.wellness.viewmodels.SettingsViewModel
import com.studyai.wellness.viewmodels.StatsViewModel
import com.studyai.wellness.viewmodels.StatsUiState

// ==================== Dashboard Tab ====================

@Composable
fun DashboardTabHomeScreen(
    navController: NavHostController,
    viewModel: DashboardViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is DashboardUiState.Loading -> {
                com.studyai.wellness.ui.components.FullScreenLoading()
            }
            is DashboardUiState.Success -> {
                DashboardTabContent(
                    dashboard = state.dashboard,
                    onItemClick = { id ->
                        // 导航到 Dashboard Tab 内的详情页
                        navController.navigate(DashboardRoute.Detail.createRoute(id))
                    },
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
private fun DashboardTabContent(
    dashboard: com.studyai.wellness.data.model.DashboardDto,
    onItemClick: (String) -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header with user avatar
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
    }
}

@Composable
private fun HabitItem(habit: com.studyai.wellness.data.model.HabitDto) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
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
            .background(Color.White, RoundedCornerShape(16.dp))
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
                            RoundedCornerShape(8.dp)
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

// 示例：Dashboard 详情页（演示 Tab 内导航）
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardDetailScreen(
    itemId: String,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail: $itemId") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "This is a detail page within Dashboard Tab",
                color = TextPrimary,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Bottom TabBar is still visible!",
                color = PrimaryGreen,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ==================== Calendar Tab ====================

@Composable
fun CalendarTabHomeScreen(
    navController: NavHostController,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val selectedDate by viewModel.selectedDate.collectAsState()
    val currentMonth by viewModel.currentMonth.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header
            Text(
                text = "Calendar",
                color = TextPrimary,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Month Navigator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = viewModel::previousMonth,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Previous Month",
                        tint = PrimaryGreen
                    )
                }

                Text(
                    text = "${currentMonth.month.getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.getDefault())} ${currentMonth.year}",
                    color = TextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                IconButton(
                    onClick = viewModel::nextMonth,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Next Month",
                        tint = PrimaryGreen
                    )
                }
            }

            // Calendar Grid
            CalendarGrid(
                currentMonth = currentMonth,
                selectedDate = selectedDate,
                onDateSelected = viewModel::selectDate
            )

            Spacer(modifier = Modifier.weight(1f))

            // Selected Date Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Selected Date",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
                Text(
                    text = "${selectedDate.month.getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.getDefault())} ${selectedDate.dayOfMonth}, ${selectedDate.year}",
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "No activities scheduled",
                    color = TextSecondary,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun CalendarGrid(
    currentMonth: java.time.YearMonth,
    selectedDate: java.time.LocalDate,
    onDateSelected: (java.time.LocalDate) -> Unit
) {
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfWeek = currentMonth.atDay(1).dayOfWeek.value % 7
    val dayNames = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Day Names Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            dayNames.forEach { day ->
                Text(
                    text = day,
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        // Calendar Days
        androidx.compose.foundation.lazy.grid.LazyVerticalGrid(
            columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(7),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            userScrollEnabled = false
        ) {
            // Empty cells for days before the first day of month
            items(firstDayOfWeek) {
                Spacer(modifier = Modifier.size(40.dp))
            }

            // Days of the month
            items(daysInMonth) { day ->
                val date = currentMonth.atDay(day)
                val isSelected = date == selectedDate
                val isToday = date == java.time.LocalDate.now()

                DayCell(
                    day = day,
                    isSelected = isSelected,
                    isToday = isToday,
                    onClick = { onDateSelected(date) }
                )
            }
        }
    }
}

@Composable
private fun DayCell(
    day: Int,
    isSelected: Boolean,
    isToday: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(
                when {
                    isSelected -> PrimaryGreen
                    isToday -> PrimaryGreen.copy(alpha = 0.2f)
                    else -> Color.Transparent
                }
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.toString(),
            color = if (isSelected) Color.White else TextPrimary,
            fontSize = 14.sp,
            fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarEventDetailScreen(
    eventId: String,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Event: $eventId") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .padding(24.dp)
        ) {
            Text(
                text = "Calendar Event Detail",
                color = TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ==================== Stats Tab ====================

@Composable
fun StatsTabHomeScreen(
    navController: NavHostController,
    viewModel: StatsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is StatsUiState.Loading -> {
                com.studyai.wellness.ui.components.FullScreenLoading()
            }
            is StatsUiState.Success -> {
                StatsTabContent(
                    stats = state.stats,
                    onNavigateToDetails = {
                        navController.navigate(StatsRoute.Details.route)
                    }
                )
            }
            is StatsUiState.Error -> {
                com.studyai.wellness.ui.components.ErrorMessage(
                    message = state.message,
                    onRetry = { viewModel.loadStats() }
                )
            }
        }
    }
}

@Composable
private fun StatsTabContent(
    stats: com.studyai.wellness.data.model.StatsDto,
    onNavigateToDetails: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Statistics",
                    color = TextPrimary,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            items(stats.overview) { metric ->
                AppMetricCard(
                    title = metric.title,
                    value = metric.value,
                    subtitle = "Tap for details"
                )
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigateToDetails() },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryGreen)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "View Detailed Stats",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsDetailsScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detailed Stats") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .padding(24.dp)
        ) {
            Text(
                text = "Detailed Statistics Page",
                color = TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ==================== Profile Tab ====================

@Composable
fun ProfileTabHomeScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is ProfileUiState.Loading -> {
                com.studyai.wellness.ui.components.FullScreenLoading()
            }
            is ProfileUiState.Success -> {
                ProfileTabContent(
                    user = state.user,
                    onEditProfile = {
                        navController.navigate(ProfileRoute.Edit.route)
                    },
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
private fun ProfileTabContent(
    user: com.studyai.wellness.data.model.UserDto,
    onEditProfile: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Text(
                    text = "My Profile",
                    color = TextPrimary,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onEditProfile() },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = user.name,
                            color = TextPrimary,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = user.email,
                            color = TextSecondary,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Tap to edit profile",
                            color = PrimaryGreen,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            item {
                com.studyai.wellness.ui.components.AppPrimaryButton(
                    text = "Log Out",
                    onClick = onLogout
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .padding(24.dp)
        ) {
            Text(
                text = "Edit Profile Page",
                color = TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ==================== Settings Tab ====================

@Composable
fun SettingsTabHomeScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
    val darkModeEnabled by viewModel.darkModeEnabled.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Settings",
                    color = TextPrimary,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                SettingItemCard(
                    title = "Push Notifications",
                    value = if (notificationsEnabled) "Enabled" else "Disabled"
                )
            }

            item {
                SettingItemCard(
                    title = "Dark Mode",
                    value = if (darkModeEnabled) "Enabled" else "Disabled"
                )
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(SettingsRoute.About.route)
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "About",
                            color = TextPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = ">",
                            color = TextSecondary,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingItemCard(
    title: String,
    value: String
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                color = TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                color = TextSecondary,
                fontSize = 14.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .padding(24.dp)
        ) {
            Text(
                text = "About App",
                color = TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
