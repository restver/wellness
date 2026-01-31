package com.studyai.wellness.data.repository

import com.studyai.wellness.data.model.*

object MockData {
    fun getDashboard(): DashboardDto = DashboardDto(
        user = UserDto(
            id = "1",
            email = "user@example.com",
            name = "Alex",
            avatar = null,
            createdAt = "2024-01-01",
            preferences = UserPreferencesDto()
        ),
        metrics = listOf(
            MetricDto(
                id = "1",
                title = "Steps",
                value = "8,432",
                subtitle = "Goal: 10,000",
                trend = "+12%"
            ),
            MetricDto(
                id = "2",
                title = "Calories",
                value = "1,842",
                subtitle = "Goal: 2,500",
                trend = "+8%"
            ),
            MetricDto(
                id = "3",
                title = "Sleep",
                value = "7h 30m",
                subtitle = "Goal: 8h",
                trend = "-4%"
            )
        ),
        habits = listOf(
            HabitDto(
                id = "1",
                name = "Morning Meditation",
                icon = "🧘",
                completed = true,
                streak = 5
            ),
            HabitDto(
                id = "2",
                name = "Drink Water",
                icon = "💧",
                completed = false,
                streak = 12
            ),
            HabitDto(
                id = "3",
                name = "Exercise",
                icon = "🏃",
                completed = true,
                streak = 3
            )
        ),
        weeklyProgress = WeeklyProgressDto(
            days = listOf(
                DayProgressDto("Mon", 0.8, true),
                DayProgressDto("Tue", 0.6, true),
                DayProgressDto("Wed", 0.9, true),
                DayProgressDto("Thu", 0.7, true),
                DayProgressDto("Fri", 0.85, true),
                DayProgressDto("Sat", 0.5, false),
                DayProgressDto("Sun", 0.4, false)
            )
        )
    )

    fun getStats(): StatsDto = StatsDto(
        overview = listOf(
            MetricDto("1", "Total Steps", "58,432", "This week"),
            MetricDto("2", "Calories Burned", "12,840", "This week"),
            MetricDto("3", "Active Hours", "42.5", "This week")
        ),
        weeklyStats = listOf(
            WeeklyStatDto("Mon", 8432.0, 10000.0),
            WeeklyStatDto("Tue", 9234.0, 10000.0),
            WeeklyStatDto("Wed", 7654.0, 10000.0),
            WeeklyStatDto("Thu", 8901.0, 10000.0),
            WeeklyStatDto("Fri", 9123.0, 10000.0),
            WeeklyStatDto("Sat", 5432.0, 10000.0),
            WeeklyStatDto("Sun", 4321.0, 10000.0)
        ),
        achievements = listOf(
            AchievementDto("1", "First Week", "Completed 7 days streak!", "🏆", "2024-01-07"),
            AchievementDto("2", "Step Master", "Reached 10k steps", "👟", null),
            AchievementDto("3", "Early Bird", "5 workouts before 8am", "🌅", null)
        ),
        goals = listOf(
            GoalDto("1", "Daily Steps", 8432.0, 10000.0, "steps", "2024-12-31"),
            GoalDto("2", "Weekly Workouts", 3.0, 5.0, "workouts", "2024-12-31"),
            GoalDto("3", "Water Intake", 1500.0, 2000.0, "ml", "2024-12-31")
        )
    )

    fun getNotifications(): List<NotificationGroupDto> = listOf(
        NotificationGroupDto(
            "Today",
            listOf(
                NotificationDto("1", "Great job!", "You've completed your morning meditation", NotificationType.ACHIEVEMENT, false, "2 hours ago"),
                NotificationDto("2", "Time to move!", "You haven't logged your exercise today", NotificationType.REMINDER, false, "5 hours ago")
            )
        ),
        NotificationGroupDto(
            "Yesterday",
            listOf(
                NotificationDto("3", "Daily Goal Met", "You reached your step goal!", NotificationType.ACHIEVEMENT, true, "Yesterday"),
                NotificationDto("4", "New Feature", "Check out the stats page", NotificationType.UPDATE, true, "Yesterday")
            )
        )
    )
}
