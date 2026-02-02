package com.studyai.wellness.controller;

import com.studyai.wellness.dto.StatsDto;
import com.studyai.wellness.service.StatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for statistics operations.
 *
 * <p>This controller handles endpoints for retrieving user statistics
 * including overview metrics, weekly stats, achievements, and goals.</p>
 */
@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
@Slf4j
public class StatsController {

    private final StatsService statsService;

    /**
     * Get statistics data for the current user.
     *
     * @param userId the ID of the user from JWT token
     * @param period the time period for statistics (e.g., "week", "month")
     * @return StatsDto containing all statistics information
     */
    @GetMapping
    public ResponseEntity<StatsDto> getStats(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "week") String period) {
        log.info("Fetching stats for user: {}, period: {}", userId, period);
        StatsDto stats = statsService.getStats(userId, period);
        return ResponseEntity.ok(stats);
    }
}
