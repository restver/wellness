package com.studyai.wellness.service;

import com.studyai.wellness.dto.UserDto;
import com.studyai.wellness.dto.UserPreferencesDto;
import com.studyai.wellness.entity.User;
import com.studyai.wellness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for handling user operations.
 *
 * <p>This service provides user profile management functionality.</p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    /**
     * Get user profile by ID.
     *
     * @param userId the ID of the user
     * @return UserDto containing user profile information
     */
    @Transactional(readOnly = true)
    public UserDto getUserProfile(Long userId) {
        log.info("Fetching user profile for user: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToUserDto(user);
    }

    /**
     * Update user preferences.
     *
     * @param userId the ID of the user
     * @param preferencesDto the new preferences
     * @return updated UserDto
     */
    @Transactional
    public UserDto updatePreferences(Long userId, UserPreferencesDto preferencesDto) {
        log.info("Updating preferences for user: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        com.studyai.wellness.entity.UserPreferences preferences =
                com.studyai.wellness.entity.UserPreferences.builder()
                        .notificationsEnabled(preferencesDto.getNotificationsEnabled())
                        .darkMode(preferencesDto.getDarkMode())
                        .language(preferencesDto.getLanguage())
                        .build();

        user.setPreferences(preferences);
        userRepository.save(user);

        return mapToUserDto(user);
    }

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
