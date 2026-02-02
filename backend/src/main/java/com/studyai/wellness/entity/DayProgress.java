package com.studyai.wellness.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entity class representing daily progress.
 *
 * <p>This entity tracks a user's progress for a single day.</p>
 */
@Entity
@Table(name = "day_progress")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The weekly progress this day belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weekly_progress_id", nullable = false)
    private WeeklyProgress weeklyProgress;

    /**
     * Date of the progress record.
     */
    @Column(nullable = false)
    private LocalDate date;

    /**
     * Day label (e.g., "Mon", "Tue").
     */
    @Column(nullable = false, length = 10)
    private String day;

    /**
     * Progress value (0.0 to 1.0).
     */
    @Column(nullable = false)
    private Double value;

    /**
     * Flag indicating if the day's goals are completed.
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean completed = false;
}
