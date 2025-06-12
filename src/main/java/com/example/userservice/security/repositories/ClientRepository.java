package com.example.userservice.security.repositories;

import java.util.Optional;

import com.example.userservice.security.models.Client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Client} entities.
 * Provides methods for retrieving clients based on their client ID.
 * 
 * @author mahip.bhatt
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    /**
     * Finds a {@link Client} by its client ID.
     *
     * @param clientId the client ID to search for
     * @return an {@link Optional} containing the found {@link Client}, or empty if not found
     */
    Optional<Client> findByClientId(String clientId);
}