package com.studyai.wellness.service;

import com.studyai.wellness.dto.*;
import com.studyai.wellness.entity.*;
import com.studyai.wellness.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for handling statistics operations.
 *
 * <p>This service provides statistics and analytics data including
 * overview metrics, weekly stats, achievements, and goals.</p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StatsService {

    private final UserRepository userRepository;
    private final MetricRepository metricRepository;
    private final GoalRepository goalRepository;
    private final AchievementRepository achievementRepository;

    /**
     * Get statistics data for a specific user.
     *
     * @param userId the ID of the user
     * @param period the time period for statistics (e.g., "week", "month")
     * @return StatsDto containing all statistics information
     */
    @Transactional(readOnly = true)
    public StatsDto getStats(Long userId, String period) {
        log.info("Fetching stats for user: {}, period: {}", userId, period);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Metric> overviewMetrics = metricRepository.findLatestMetricsByUserId(userId);
        List<Goal> goals = goalRepository.findByUserIdAndActiveTrueOrderByDeadlineAsc(userId);
        List<Achievement> achievements = achievementRepository.findByUserIdOrderByUnlockedAtDesc(userId);

        return StatsDto.builder()
                .overview(mapToMetricDtos(overviewMetrics))
                .weeklyStats(generateWeeklyStats(user))
                .achievements(mapToAchievementDtos(achievements))
                .goals(mapToGoalDtos(goals))
                .build();
    }

    /**
     * Generate weekly statistics for the user.
     *
     * @param user the user entity
     * @return list of WeeklyStatDto
     */
    private List<WeeklyStatDto> generateWeeklyStats(User user) {
        return List.of(
                WeeklyStatDto.builder()
                        .label("Activity")
                        .value(5.2)
                        .target(7.0)
                        .build(),
                WeeklyStatDto.builder()
                        .label("Calories")
                        .value(12450.0)
                        .target(14000.0)
                        .build(),
                WeeklyStatDto.builder()
                        .label("Sleep")
                        .value(7.2)
                        .target(8.0)
                        .build(),
                WeeklyStatDto.builder()
                        .label("Steps")
                        .value(45000.0)
                        .target(70000.0)
                        .build()
        );
    }

    private List<MetricDto> mapToMetricDtos(List<Metric> metrics) {
        return metrics.stream()
                .map(m -> MetricDto.builder()
                        .id(m.getId().toString())
                        .title(m.getTitle())
                        .value(m.getValue())
                        .subtitle(m.getSubtitle())
                        .trend(m.getTrend())
                        .color(m.getColor())
                        .build())
                .toList();
    }

    private List<AchievementDto> mapToAchievementDtos(List<Achievement> achievements) {
        return achievements.stream()
                .map(a -> AchievementDto.builder()
                        .id(a.getId().toString())
                        .title(a.getTitle())
                        .description(a.getDescription())
                        .icon(a.getIcon())
                        .unlockedAt(a.getUnlockedAt())
                        .build())
                .toList();
    }

    private List<GoalDto> mapToGoalDtos(List<Goal> goals) {
        return goals.stream()
                .map(g -> GoalDto.builder()
                        .id(g.getId().toString())
                        .title(g.getTitle())
                        .current(g.getCurrent())
                        .target(g.getTarget())
                        .unit(g.getUnit())
                        .deadline(g.getDeadline())
                        .build())
                .toList();
    }
}
