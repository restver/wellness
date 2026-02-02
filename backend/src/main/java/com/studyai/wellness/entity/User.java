package com.studyai.wellness.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a User in the Wellness application.
 *
 * <p>This entity stores user account information including credentials,
 * profile data, and user preferences.</p>
 */
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User's email address, used as login username.
     */
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /**
     * User's password (hashed).
     */
    @Column(nullable = false)
    private String password;

    /**
     * User's display name.
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * URL to user's avatar image.
     */
    @Column(length = 500)
    private String avatar;

    /**
     * Timestamp when the user account was created.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when the user last logged in.
     */
    private LocalDateTime lastLoginAt;

    /**
     * Flag indicating if the account is active.
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    /**
     * User's preferences settings.
     */
    @Embedded
    private UserPreferences preferences;

    /**
     * User's habits.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Habit> habits = new ArrayList<>();

    /**
     * User's metrics.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Metric> metrics = new ArrayList<>();

    /**
     * User's goals.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Goal> goals = new ArrayList<>();

    /**
     * User's notifications.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (preferences == null) {
            preferences = UserPreferences.builder().build();
        }
    }
}
