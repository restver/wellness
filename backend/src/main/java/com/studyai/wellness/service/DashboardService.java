package com.studyai.wellness.service;

import com.studyai.wellness.dto.*;
import com.studyai.wellness.entity.*;
import com.studyai.wellness.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling dashboard operations.
 *
 * <p>This service provides dashboard data aggregation including
 * user metrics, habits, and weekly progress.</p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {

    private final UserRepository userRepository;
    private final HabitRepository habitRepository;
    private final MetricRepository metricRepository;

    /**
     * Get dashboard data for a specific user.
     *
     * @param userId the ID of the user
     * @return DashboardDto containing all dashboard information
     */
    @Transactional(readOnly = true)
    public DashboardDto getDashboard(Long userId) {
        log.info("Fetching dashboard for user: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Metric> metrics = metricRepository.findLatestMetricsByUserId(userId);
        List<Habit> habits = habitRepository.findByUserIdAndActiveTrueOrderByDisplayOrderAsc(userId);
        WeeklyProgress weeklyProgress = generateWeeklyProgress(user);

        return DashboardDto.builder()
                .user(mapToUserDto(user))
                .metrics(mapToMetricDtos(metrics))
                .habits(mapToHabitDtos(habits))
                .weeklyProgress(mapToWeeklyProgressDto(weeklyProgress))
                .build();
    }

    /**
     * Generate weekly progress data for the user.
     *
     * @param user the user entity
     * @return WeeklyProgress entity
     */
    private WeeklyProgress generateWeeklyProgress(User user) {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);

        List<DayProgress> days = new ArrayList<>();
        String[] dayNames = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

        for (int i = 0; i < 7; i++) {
            LocalDate date = startOfWeek.plusDays(i);
            boolean isCompleted = calculateDayCompletion(user, date);

            DayProgress dayProgress = DayProgress.builder()
                    .day(dayNames[i])
                    .date(date)
                    .value(isCompleted ? 1.0 : 0.0)
                    .completed(isCompleted)
                    .weeklyProgress(null) // Not persisted, set to null
                    .build();

            days.add(dayProgress);
        }

        return WeeklyProgress.builder()
                .user(user)
                .weekStartDate(startOfWeek)
                .days(days)
                .build();
    }

    /**
     * Calculate completion status for a specific day.
     *
     * @param user the user entity
     * @param date the date to check
     * @return true if the day's goals are completed
     */
    private boolean calculateDayCompletion(User user, LocalDate date) {
        // Simple logic: if more than 50% of habits are completed
        List<Habit> habits = habitRepository.findByUserIdAndActiveTrue(user.getId());
        if (habits.isEmpty()) {
            return false;
        }

        long completedCount = habits.stream()
                .filter(Habit::getCompleted)
                .count();

        return completedCount > habits.size() / 2;
    }

    private UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId().toString())
                .email(user.getEmail())
                .name(user.getName())
                .avatar(user.getAvatar())
                .createdAt(user.getCreatedAt())
                .preferences(mapToPreferencesDto(user.getPreferences()))
                .build();
    }

    private UserPreferencesDto mapToPreferencesDto(com.studyai.wellness.entity.UserPreferences preferences) {
        if (preferences == null) {
            return UserPreferencesDto.builder().build();
        }
        return UserPreferencesDto.builder()
                .notificationsEnabled(preferences.getNotificationsEnabled())
                .darkMode(preferences.getDarkMode())
                .language(preferences.getLanguage())
                .build();
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

    private List<HabitDto> mapToHabitDtos(List<Habit> habits) {
        return habits.stream()
                .map(h -> HabitDto.builder()
                        .id(h.getId().toString())
                        .name(h.getName())
                        .icon(h.getIcon())
                        .completed(h.getCompleted())
                        .streak(h.getStreak())
                        .build())
                .toList();
    }

    private WeeklyProgressDto mapToWeeklyProgressDto(WeeklyProgress weeklyProgress) {
        List<DayProgressDto> dayDtos = weeklyProgress.getDays().stream()
                .map(d -> DayProgressDto.builder()
                        .day(d.getDay())
                        .value(d.getValue())
                        .completed(d.getCompleted())
                        .build())
                .toList();

        return WeeklyProgressDto.builder()
                .days(dayDtos)
                .build();
    }
}
