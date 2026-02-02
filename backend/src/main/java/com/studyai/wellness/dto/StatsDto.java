package com.studyai.wellness.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for Statistics information.
 *
 * <p>This DTO aggregates various statistics including overview metrics,
 * weekly stats, achievements, and goals.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsDto {

    /**
     * List of overview metrics.
     */
    private List<MetricDto> overview;

    /**
     * List of weekly statistics.
     */
    private List<WeeklyStatDto> weeklyStats;

    /**
     * List of user achievements.
     */
    private List<AchievementDto> achievements;

    /**
     * List of user goals.
     */
    private List<GoalDto> goals;
}
