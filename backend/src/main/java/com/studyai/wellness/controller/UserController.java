package com.studyai.wellness.controller;

import com.studyai.wellness.dto.UserDto;
import com.studyai.wellness.dto.UserPreferencesDto;
import com.studyai.wellness.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for user operations.
 *
 * <p>This controller handles endpoints for user profile management.</p>
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Get the current user's profile.
     *
     * @param userId the ID of the user from JWT token
     * @return UserDto containing user profile information
     */
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(@RequestParam Long userId) {
        log.info("Fetching user profile for user: {}", userId);
        UserDto user = userService.getUserProfile(userId);
        return ResponseEntity.ok(user);
    }

    /**
     * Update the current user's preferences.
     *
     * @param userId the ID of the user from JWT token
     * @param preferencesDto the new preferences
     * @return updated UserDto
     */
    @PutMapping("/preferences")
    public ResponseEntity<UserDto> updatePreferences(
            @RequestParam Long userId,
            @Valid @RequestBody UserPreferencesDto preferencesDto) {
        log.info("Updating preferences for user: {}", userId);
        UserDto user = userService.updatePreferences(userId, preferencesDto);
        return ResponseEntity.ok(user);
    }
}
