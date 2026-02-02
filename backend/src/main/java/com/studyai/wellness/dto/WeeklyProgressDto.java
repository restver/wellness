package com.studyai.wellness.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for Weekly Progress.
 *
 * <p>This DTO contains progress data for each day of the week.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyProgressDto {

    /**
     * List of daily progress data.
     */
    private List<DayProgressDto> days;
}
