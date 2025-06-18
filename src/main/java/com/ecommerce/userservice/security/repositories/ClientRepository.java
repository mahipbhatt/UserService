package com.ecommerce.userservice.security.repositories;

import com.ecommerce.userservice.security.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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