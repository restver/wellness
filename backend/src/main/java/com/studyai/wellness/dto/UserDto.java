package com.studyai.wellness.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for User information.
 *
 * <p>This DTO is used to transfer user data between the client and server,
 * excluding sensitive information like passwords.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    /**
     * Unique identifier of the user.
     */
    private String id;

    /**
     * User's email address.
     */
    private String email;

    /**
     * User's display name.
     */
    private String name;

    /**
     * URL to user's avatar image.
     */
    private String avatar;

    /**
     * Timestamp when the account was created.
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createdAt;

    /**
     * User's preferences settings.
     */
    private UserPreferencesDto preferences;
}
