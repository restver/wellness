package com.studyai.wellness.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Weekly Statistics.
 *
 * <p>This DTO represents a single weekly statistic.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyStatDto {

    /**
     * Label for the statistic.
     */
    private String label;

    /**
     * Current value.
     */
    private Double value;

    /**
     * Target value.
     */
    private Double target;
}
