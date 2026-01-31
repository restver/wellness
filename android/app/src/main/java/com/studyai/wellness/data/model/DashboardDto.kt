package com.studyai.wellness.data.model

data class DashboardDto(
    val user: UserDto,
    val metrics: List<MetricDto>,
    val habits: List<HabitDto>,
    val weeklyProgress: WeeklyProgressDto
)

data class MetricDto(
    val id: String,
    val title: String,
    val value: String,
    val subtitle: String? = null,
    val trend: String? = null,
    val color: String? = null
)

data class HabitDto(
    val id: String,
    val name: String,
    val icon: String,
    val completed: Boolean,
    val streak: Int
)

data class WeeklyProgressDto(
    val days: List<DayProgressDto>
)

data class DayProgressDto(
    val day: String,
    val value: Double,
    val completed: Boolean
)
