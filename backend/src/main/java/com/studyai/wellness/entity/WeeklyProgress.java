package com.studyai.wellness.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing weekly progress tracking.
 *
 * <p>This entity stores a user's daily progress for a week.</p>
 */
@Entity
@Table(name = "weekly_progress")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who owns this progress data.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Start date of the week.
     */
    @Column(nullable = false)
    private LocalDate weekStartDate;

    /**
     * List of daily progress data.
     */
    @OneToMany(mappedBy = "weeklyProgress", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("date ASC")
    private List<DayProgress> days = new ArrayList<>();
}
