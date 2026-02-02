package com.studyai.wellness.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for User Preferences.
 *
 * <p>This DTO contains user-specific settings and preferences.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferencesDto {

    /**
     * Flag indicating whether notifications are enabled.
     */
    @Builder.Default
    private Boolean notificationsEnabled = true;

    /**
     * Flag indicating whether dark mode is enabled.
     */
    @Builder.Default
    private Boolean darkMode = false;

    /**
     * User's preferred language code.
     */
    @Builder.Default
    private String language = "en";
}
