package com.studyai.wellness.data.model

data class UserDto(
    val id: String,
    val email: String,
    val name: String,
    val avatar: String? = null,
    val createdAt: String,
    val preferences: UserPreferencesDto
)

data class UserPreferencesDto(
    val notificationsEnabled: Boolean = true,
    val darkMode: Boolean = false,
    val language: String = "en"
)

data class LoginRequestDto(
    val email: String,
    val password: String
)

data class LoginResponseDto(
    val user: UserDto,
    val token: String,
    val refreshToken: String
)

data class ForgotPasswordRequestDto(
    val email: String
)

data class ResetPasswordRequestDto(
    val token: String,
    val newPassword: String
)
