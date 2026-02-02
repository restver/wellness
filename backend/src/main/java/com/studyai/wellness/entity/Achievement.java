package com.studyai.wellness.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity class representing a user achievement.
 *
 * <p>This entity tracks user's achievements and milestones
 * such as streak completions, goal achievements, etc.</p>
 */
@Entity
@Table(name = "achievements")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who earned this achievement.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Title of the achievement.
     */
    @Column(nullable = false, length = 100)
    private String title;

    /**
     * Detailed description of the achievement.
     */
    @Column(nullable = false, length = 500)
    private String description;

    /**
     * Icon representing the achievement (emoji or icon name).
     */
    @Column(length = 50)
    private String icon;

    /**
     * Timestamp when the achievement was unlocked.
     */
    private LocalDateTime unlockedAt;

    /**
     * Whether the achievement is unlocked.
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean unlocked = false;
}
