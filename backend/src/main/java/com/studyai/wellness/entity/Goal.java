package com.studyai.wellness.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entity class representing a user goal.
 *
 * <p>This entity tracks user's fitness and wellness goals
 * such as weekly exercise targets, weight loss goals, etc.</p>
 */
@Entity
@Table(name = "goals")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who set this goal.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Title of the goal.
     */
    @Column(nullable = false, length = 100)
    private String title;

    /**
     * Current progress value.
     */
    @Column(nullable = false)
    private Double current;

    /**
     * Target value to achieve.
     */
    @Column(nullable = false)
    private Double target;

    /**
     * Unit of measurement (e.g., "kg", "hours", "steps").
     */
    @Column(length = 20)
    private String unit;

    /**
     * Deadline for achieving the goal.
     */
    @Column(nullable = false)
    private LocalDate deadline;

    /**
     * Whether the goal is achieved.
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean achieved = false;

    /**
     * Whether the goal is active.
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;
}
