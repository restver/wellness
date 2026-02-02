package com.studyai.wellness.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Daily Progress.
 *
 * <p>This DTO represents progress data for a single day.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayProgressDto {

    /**
     * Day label (e.g., "Mon", "Tue").
     */
    private String day;

    /**
     * Progress value (0.0 to 1.0).
     */
    private Double value;

    /**
     * Flag indicating if the day's goals are completed.
     */
    private Boolean completed;
}
