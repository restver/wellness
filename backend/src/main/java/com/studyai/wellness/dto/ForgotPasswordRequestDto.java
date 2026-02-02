package com.studyai.wellness.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Forgot Password Request.
 *
 * <p>This DTO is used to request a password reset email.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordRequestDto {

    /**
     * User's email address for password reset.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
}
