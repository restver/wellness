package com.studyai.wellness.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for Dashboard information.
 *
 * <p>This DTO aggregates user metrics, habits, and weekly progress
 * for the dashboard view.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDto {

    /**
     * User information.
     */
    private UserDto user;

    /**
     * List of health/fitness metrics.
     */
    private List<MetricDto> metrics;

    /**
     * List of daily habits.
     */
    private List<HabitDto> habits;

    /**
     * Weekly progress data.
     */
    private WeeklyProgressDto weeklyProgress;
}
