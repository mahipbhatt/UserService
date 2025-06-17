package com.ecommerce.userservice.repositories;

import com.ecommerce.userservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Session} entities.
 * Provides CRUD operations and custom queries for session management.
 *
 * @author mahip.bhatt
 */
public interface SessionRepository extends JpaRepository<Session, Long> {

    /**
     * Retrieves a session by its token and the associated user's ID.
     *
     * @param token the session token
     * @param userId the ID of the associated user
     * @return an {@link Optional} containing the matching {@link Session}, if found
     */
    Optional<Session> findByTokenAndUser_Id(String token, Long userId);
}