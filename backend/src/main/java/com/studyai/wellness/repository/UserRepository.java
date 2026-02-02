package com.studyai.wellness.repository;

import com.studyai.wellness.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity.
 *
 * <p>This repository provides data access methods for User entities.</p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by email address.
     *
     * @param email the email address to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if a user exists by email address.
     *
     * @param email the email address to check
     * @return true if a user with the email exists
     */
    boolean existsByEmail(String email);

    /**
     * Find a user by email with active status.
     *
     * @param email the email address to search for
     * @return Optional containing the active user if found
     */
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.active = true")
    Optional<User> findActiveUserByEmail(String email);
}
