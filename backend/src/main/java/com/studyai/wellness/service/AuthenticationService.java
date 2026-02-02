package com.studyai.wellness.service;

import com.studyai.wellness.dto.*;
import com.studyai.wellness.entity.Notification;
import com.studyai.wellness.entity.User;
import com.studyai.wellness.repository.UserRepository;
import com.studyai.wellness.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for handling authentication operations.
 *
 * <p>This service provides user authentication, login, logout, and
 * password management functionality.</p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final NotificationService notificationService;

    /**
     * Authenticate a user with email and password.
     *
     * @param request the login request containing email and password
     * @return LoginResponseDto containing user info and JWT tokens
     * @throws RuntimeException if authentication fails
     */
    @Transactional
    public LoginResponseDto login(LoginRequestDto request) {
        log.info("Attempting login for user: {}", request.getEmail());

        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Get the user from repository
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update last login time
        user.setLastLoginAt(java.time.LocalDateTime.now());
        userRepository.save(user);

        // Generate JWT tokens
        String token = tokenProvider.generateToken(user.getEmail(), user.getId().toString());
        String refreshToken = tokenProvider.generateRefreshToken(user.getEmail(), user.getId().toString());

        // Create welcome notification for new logins
        if (user.getCreatedAt().isEqual(user.getLastLoginAt()) ||
                user.getLastLoginAt().minusDays(1).isAfter(java.time.LocalDateTime.now())) {
            notificationService.createWelcomeNotification(user);
        }

        log.info("Login successful for user: {}", request.getEmail());

        return LoginResponseDto.builder()
                .user(mapToUserDto(user))
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * Handle forgot password request.
     *
     * <p>In a production environment, this would send an email with a reset link.
     * For this demo, we'll just log the request.</p>
     *
     * @param request the forgot password request containing email
     */
    public void forgotPassword(ForgotPasswordRequestDto request) {
        log.info("Password reset requested for email: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // In production, generate a reset token and send email
        // For now, we'll just create a notification
        notificationService.createNotification(
                user,
                "Password Reset",
                "A password reset was requested for your account. If this wasn't you, please contact support.",
                Notification.NotificationType.ALERT
        );

        log.info("Password reset email sent to: {}", request.getEmail());
    }

    /**
     * Logout the current user.
     *
     * <p>In a stateless JWT setup, logout is handled on the client side
     * by removing the token. This method logs the logout event.</p>
     *
     * @param userId the ID of the user logging out
     */
    public void logout(Long userId) {
        log.info("User logged out: {}", userId);
        // In production, you might want to:
        // 1. Add the token to a blacklist (if using token invalidation)
        // 2. Log the logout event
        // 3. Update user statistics
    }

    /**
     * Map User entity to UserDto.
     *
     * @param user the user entity
     * @return UserDto
     */
    private UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId().toString())
                .email(user.getEmail())
                .name(user.getName())
                .avatar(user.getAvatar())
                .createdAt(user.getCreatedAt())
                .preferences(mapToPreferencesDto(user.getPreferences()))
                .build();
    }

    /**
     * Map UserPreferences to UserPreferencesDto.
     *
     * @param preferences the user preferences entity
     * @return UserPreferencesDto
     */
    private UserPreferencesDto mapToPreferencesDto(com.studyai.wellness.entity.UserPreferences preferences) {
        if (preferences == null) {
            return UserPreferencesDto.builder().build();
        }
        return UserPreferencesDto.builder()
                .notificationsEnabled(preferences.getNotificationsEnabled())
                .darkMode(preferences.getDarkMode())
                .language(preferences.getLanguage())
                .build();
    }
}
