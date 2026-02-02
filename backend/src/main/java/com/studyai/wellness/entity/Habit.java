package com.studyai.wellness.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a daily habit.
 *
 * <p>This entity tracks user's daily habits such as meditation,
 * exercise, reading, etc.</p>
 */
@Entity
@Table(name = "habits")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who owns this habit.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Name of the habit.
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Icon representing the habit (emoji or icon name).
     */
    @Column(length = 50)
    private String icon;

    /**
     * Flag indicating if the habit is completed today.
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean completed = false;

    /**
     * Current streak count (consecutive days completed).
     */
    @Column(nullable = false)
    @Builder.Default
    private Integer streak = 0;

    /**
     * Order in which this habit should be displayed.
     */
    @Column(nullable = false)
    @Builder.Default
    private Integer displayOrder = 0;

    /**
     * Whether this habit is active.
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;
}
