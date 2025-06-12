package com.example.userservice.repositories;

import com.example.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 * Provides CRUD operations and custom queries for user management.
 *
 * @author mahip.bhatt
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email address of the user
     * @return an {@link Optional} containing the matching {@link User}, if found
     */
    Optional<User> findByEmail(String email);
}