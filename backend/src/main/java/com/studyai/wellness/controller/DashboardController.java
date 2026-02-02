package com.studyai.wellness.controller;

import com.studyai.wellness.dto.DashboardDto;
import com.studyai.wellness.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for dashboard operations.
 *
 * <p>This controller handles endpoints for retrieving dashboard data
 * including user metrics, habits, and weekly progress.</p>
 */
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * Get dashboard data for the current user.
     *
     * @param userId the ID of the user from JWT token
     * @return DashboardDto containing all dashboard information
     */
    @GetMapping
    public ResponseEntity<DashboardDto> getDashboard(@RequestParam Long userId) {
        log.info("Fetching dashboard for user: {}", userId);
        DashboardDto dashboard = dashboardService.getDashboard(userId);
        return ResponseEntity.ok(dashboard);
    }
}
