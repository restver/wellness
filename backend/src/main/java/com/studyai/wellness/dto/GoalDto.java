package com.studyai.wellness.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Data Transfer Object for Goal information.
 *
 * <p>This DTO represents a user's goal.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalDto {

    /**
     * Unique identifier of the goal.
     */
    private String id;

    /**
     * Title of the goal.
     */
    private String title;

    /**
     * Current progress value.
     */
    private Double current;

    /**
     * Target value to achieve.
     */
    private Double target;

    /**
     * Unit of measurement.
     */
    private String unit;

    /**
     * Deadline for achieving the goal.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;
}
