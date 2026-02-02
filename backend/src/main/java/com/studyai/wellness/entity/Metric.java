package com.studyai.wellness.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a health/fitness metric.
 *
 * <p>This entity tracks user's health metrics such as calories burned,
 * active minutes, sleep hours, water intake, etc.</p>
 */
@Entity
@Table(name = "metrics")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who owns this metric.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Title of the metric (e.g., "Calories Burned").
     */
    @Column(nullable = false, length = 100)
    private String title;

    /**
     * Current value of the metric.
     */
    @Column(nullable = false, length = 50)
    private String value;

    /**
     * Additional description or subtitle.
     */
    @Column(length = 100)
    private String subtitle;

    /**
     * Trend indicator (e.g., "+15%", "-10%").
     */
    @Column(length = 20)
    private String trend;

    /**
     * Color code for UI display (hex color).
     */
    @Column(length = 20)
    private String color;

    /**
     * Date when this metric was recorded.
     */
    @Column(nullable = false)
    private String recordDate;
}
