package com.studyai.wellness.config;

import com.studyai.wellness.entity.*;
import com.studyai.wellness.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data initializer for populating the database with sample data.
 *
 * <p>This class creates initial test data for development and testing purposes.</p>
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final HabitRepository habitRepository;
    private final MetricRepository metricRepository;
    private final GoalRepository goalRepository;
    private final NotificationRepository notificationRepository;
    private final AchievementRepository achievementRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            log.info("Initializing sample data...");
            createSampleUser();
            log.info("Sample data initialized successfully.");
        } else {
            log.info("Database already contains data, skipping initialization.");
        }
    }

    private void createSampleUser() {
        // Create user
        User user = User.builder()
                .email("user@example.com")
                .password(passwordEncoder.encode("password123"))
                .name("Sarah")
                .avatar(null)
                .createdAt(LocalDateTime.now())
                .active(true)
                .preferences(UserPreferences.builder()
                        .notificationsEnabled(true)
                        .darkMode(false)
                        .language("en")
                        .build())
                .build();

        user = userRepository.save(user);
        log.info("Created user: {}", user.getEmail());

        // Create habits
        createHabits(user);

        // Create metrics
        createMetrics(user);

        // Create goals
        createGoals(user);

        // Create notifications
        createNotifications(user);

        // Create achievements
        createAchievements(user);
    }

    private void createHabits(User user) {
        Habit[] habits = {
                Habit.builder()
                        .user(user)
                        .name("Morning Meditation")
                        .icon("🧘")
                        .completed(true)
                        .streak(7)
                        .displayOrder(0)
                        .active(true)
                        .build(),
                Habit.builder()
                        .user(user)
                        .name("Drink Water")
                        .icon("💧")
                        .completed(true)
                        .streak(14)
                        .displayOrder(1)
                        .active(true)
                        .build(),
                Habit.builder()
                        .user(user)
                        .name("Exercise")
                        .icon("🏃")
                        .completed(false)
                        .streak(3)
                        .displayOrder(2)
                        .active(true)
                        .build(),
                Habit.builder()
                        .user(user)
                        .name("Read Book")
                        .icon("📚")
                        .completed(true)
                        .streak(21)
                        .displayOrder(3)
                        .active(true)
                        .build(),
                Habit.builder()
                        .user(user)
                        .name("No Sugar")
                        .icon("🍬")
                        .completed(false)
                        .streak(5)
                        .displayOrder(4)
                        .active(true)
                        .build()
        };

        for (Habit habit : habits) {
            habitRepository.save(habit);
        }
        log.info("Created {} habits for user", habits.length);
    }

    private void createMetrics(User user) {
        String today = LocalDate.now().toString();

        Metric[] metrics = {
                Metric.builder()
                        .user(user)
                        .title("Calories Burned")
                        .value("1,245")
                        .subtitle("of 2,000 goal")
                        .trend("+15%")
                        .color("#3D8A5A")
                        .recordDate(today)
                        .build(),
                Metric.builder()
                        .user(user)
                        .title("Active Minutes")
                        .value("45")
                        .subtitle("of 60 goal")
                        .trend("+8%")
                        .color("#5CAD7A")
                        .recordDate(today)
                        .build(),
                Metric.builder()
                        .user(user)
                        .title("Sleep Hours")
                        .value("7.5")
                        .subtitle("hours last night")
                        .trend("+5%")
                        .color("#3D8A5A")
                        .recordDate(today)
                        .build(),
                Metric.builder()
                        .user(user)
                        .title("Water Intake")
                        .value("1.5L")
                        .subtitle("of 2L goal")
                        .trend("-10%")
                        .color("#F5A623")
                        .recordDate(today)
                        .build()
        };

        for (Metric metric : metrics) {
            metricRepository.save(metric);
        }
        log.info("Created {} metrics for user", metrics.length);
    }

    private void createGoals(User user) {
        Goal[] goals = {
                Goal.builder()
                        .user(user)
                        .title("Weekly Exercise")
                        .current(5.2)
                        .target(7.0)
                        .unit("hours")
                        .deadline(LocalDate.now().plusWeeks(1))
                        .achieved(false)
                        .active(true)
                        .build(),
                Goal.builder()
                        .user(user)
                        .title("Monthly Steps")
                        .current(180000.0)
                        .target(300000.0)
                        .unit("steps")
                        .deadline(LocalDate.now().plusMonths(1))
                        .achieved(false)
                        .active(true)
                        .build()
        };

        for (Goal goal : goals) {
            goalRepository.save(goal);
        }
        log.info("Created {} goals for user", goals.length);
    }

    private void createNotifications(User user) {
        Notification[] notifications = {
                Notification.builder()
                        .user(user)
                        .title("Achievement Unlocked!")
                        .message("You've completed 7 days of meditation streak!")
                        .type(Notification.NotificationType.ACHIEVEMENT)
                        .read(false)
                        .createdAt(LocalDateTime.now().minusHours(1))
                        .build(),
                Notification.builder()
                        .user(user)
                        .title("Daily Reminder")
                        .message("Don't forget to log your water intake today.")
                        .type(Notification.NotificationType.REMINDER)
                        .read(false)
                        .createdAt(LocalDateTime.now().minusHours(2))
                        .build(),
                Notification.builder()
                        .user(user)
                        .title("Progress Update")
                        .message("You're 80% towards your weekly goal!")
                        .type(Notification.NotificationType.UPDATE)
                        .read(true)
                        .createdAt(LocalDateTime.now().minusDays(1))
                        .build(),
                Notification.builder()
                        .user(user)
                        .title("Welcome")
                        .message("Thanks for joining Wellness! Let's start your journey.")
                        .type(Notification.NotificationType.UPDATE)
                        .read(true)
                        .createdAt(LocalDateTime.now().minusDays(2))
                        .build()
        };

        for (Notification notification : notifications) {
            notificationRepository.save(notification);
        }
        log.info("Created {} notifications for user", notifications.length);
    }

    private void createAchievements(User user) {
        Achievement[] achievements = {
                Achievement.builder()
                        .user(user)
                        .title("7-Day Streak")
                        .description("Completed habits for 7 consecutive days")
                        .icon("🔥")
                        .unlocked(true)
                        .unlockedAt(LocalDateTime.now().minusDays(1))
                        .build(),
                Achievement.builder()
                        .user(user)
                        .title("Early Bird")
                        .description("Completed morning meditation for 14 days")
                        .icon("🌅")
                        .unlocked(true)
                        .unlockedAt(LocalDateTime.now().minusDays(3))
                        .build(),
                Achievement.builder()
                        .user(user)
                        .title("Marathon Reader")
                        .description("Read for 21 consecutive days")
                        .icon("📖")
                        .unlocked(true)
                        .unlockedAt(LocalDateTime.now())
                        .build()
        };

        for (Achievement achievement : achievements) {
            achievementRepository.save(achievement);
        }
        log.info("Created {} achievements for user", achievements.length);
    }
}
