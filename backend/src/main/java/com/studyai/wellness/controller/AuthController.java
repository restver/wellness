package com.studyai.wellness.controller;

import com.studyai.wellness.dto.ForgotPasswordRequestDto;
import com.studyai.wellness.dto.LoginRequestDto;
import com.studyai.wellness.dto.LoginResponseDto;
import com.studyai.wellness.service.AuthenticationService;
import com.studyai.wellness.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for authentication operations.
 *
 * <p>This controller handles endpoints for user authentication including
 * login, logout, and password management.</p>
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationService authenticationService;

    /**
     * Authenticate a user with email and password.
     *
     * @param request the login request containing email and password
     * @return LoginResponseDto with user info and JWT tokens
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        log.info("Login request received for email: {}", request.getEmail());
        LoginResponseDto response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Request a password reset email.
     *
     * @param request the forgot password request containing email
     * @return 204 No Content on success
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDto request) {
        log.info("Password reset requested for email: {}", request.getEmail());
        authenticationService.forgotPassword(request);
        return ResponseEntity.noContent().build();
    }

    /**
     * Logout the current user.
     *
     * <p>Note: In a stateless JWT setup, logout is primarily handled
     * by the client removing the token. This endpoint logs the event.</p>
     *
     * @param userId the ID of the user from the JWT token
     * @return 204 No Content
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam Long userId) {
        log.info("Logout request received for user: {}", userId);
        authenticationService.logout(userId);
        return ResponseEntity.noContent().build();
    }
}
