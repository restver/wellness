package com.studyai.wellness.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Embeddable class representing user preferences.
 *
 * <p>This class stores user-specific settings and preferences
 * such as notification settings, theme, and language.</p>
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferences {

    /**
     * Flag indicating whether push notifications are enabled.
     */
    @Builder.Default
    private Boolean notificationsEnabled = true;

    /**
     * Flag indicating whether dark mode is enabled.
     */
    @Builder.Default
    private Boolean darkMode = false;

    /**
     * User's preferred language code (e.g., "en", "zh", "es").
     */
    @Builder.Default
    private String language = "en";
}
