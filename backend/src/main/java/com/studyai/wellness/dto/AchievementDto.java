package com.studyai.wellness.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Achievement information.
 *
 * <p>This DTO represents a user achievement.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AchievementDto {

    /**
     * Unique identifier of the achievement.
     */
    private String id;

    /**
     * Title of the achievement.
     */
    private String title;

    /**
     * Detailed description.
     */
    private String description;

    /**
     * Icon representing the achievement.
     */
    private String icon;

    /**
     * Timestamp when the achievement was unlocked.
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime unlockedAt;
}
