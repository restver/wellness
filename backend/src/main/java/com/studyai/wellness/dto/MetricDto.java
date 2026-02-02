package com.studyai.wellness.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Metric information.
 *
 * <p>This DTO represents a health/fitness metric displayed on the dashboard.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricDto {

    /**
     * Unique identifier of the metric.
     */
    private String id;

    /**
     * Title of the metric.
     */
    private String title;

    /**
     * Current value of the metric.
     */
    private String value;

    /**
     * Additional description or subtitle.
     */
    private String subtitle;

    /**
     * Trend indicator (e.g., "+15%").
     */
    private String trend;

    /**
     * Color code for UI display.
     */
    private String color;
}
