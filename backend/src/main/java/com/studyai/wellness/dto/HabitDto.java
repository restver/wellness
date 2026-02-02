package com.studyai.wellness.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Habit information.
 *
 * <p>This DTO represents a daily habit tracked by the user.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabitDto {

    /**
     * Unique identifier of the habit.
     */
    private String id;

    /**
     * Name of the habit.
     */
    private String name;

    /**
     * Icon representing the habit.
     */
    private String icon;

    /**
     * Flag indicating if the habit is completed today.
     */
    private Boolean completed;

    /**
     * Current streak count.
     */
    private Integer streak;
}
