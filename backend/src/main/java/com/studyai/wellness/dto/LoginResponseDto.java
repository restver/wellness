package com.studyai.wellness.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Login Response.
 *
 * <p>This DTO contains the authentication tokens and user information
 * returned after successful login.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    /**
     * User information.
     */
    private UserDto user;

    /**
     * JWT access token.
     */
    private String token;

    /**
     * JWT refresh token.
     */
    private String refreshToken;
}
