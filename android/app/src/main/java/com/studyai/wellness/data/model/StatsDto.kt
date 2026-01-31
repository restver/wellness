package com.studyai.wellness.data.model

data class StatsDto(
    val overview: List<MetricDto>,
    val weeklyStats: List<WeeklyStatDto>,
    val achievements: List<AchievementDto>,
    val goals: List<GoalDto>
)

data class WeeklyStatDto(
    val label: String,
    val value: Double,
    val target: Double
)

data class AchievementDto(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val unlockedAt: String? = null
)

data class GoalDto(
    val id: String,
    val title: String,
    val current: Double,
    val target: Double,
    val unit: String,
    val deadline: String
)
