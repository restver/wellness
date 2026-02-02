package com.studyai.wellness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for Wellness Backend.
 *
 * <p>This is the entry point for the Spring Boot application that provides
 * backend services for the Wellness mobile application.</p>
 *
 * @author Wellness Team
 * @version 1.0.0
 * @since 2024
 */
@SpringBootApplication
public class WellnessBackendApplication {

    /**
     * Main method that starts the Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(WellnessBackendApplication.class, args);
    }
}
