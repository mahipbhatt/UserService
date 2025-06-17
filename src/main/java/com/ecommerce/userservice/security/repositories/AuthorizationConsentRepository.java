package com.ecommerce.userservice.security.repositories;

import java.util.Optional;

import com.ecommerce.userservice.security.models.AuthorizationConsent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link AuthorizationConsent} entities.
 * Provides methods for retrieving and deleting authorization consents based on client ID and principal name.
 * 
 * @author mahip.bhatt
 */
@Repository
public interface AuthorizationConsentRepository extends JpaRepository<AuthorizationConsent, AuthorizationConsent.AuthorizationConsentId> {

    /**
     * Finds an {@link AuthorizationConsent} by the registered client ID and principal name.
     *
     * @param registeredClientId the ID of the registered client
     * @param principalName the name of the principal (user)
     * @return an {@link Optional} containing the found {@link AuthorizationConsent}, or empty if not found
     */
    Optional<AuthorizationConsent> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);

    /**
     * Deletes an {@link AuthorizationConsent} by the registered client ID and principal name.
     *
     * @param registeredClientId the ID of the registered client
     * @param principalName the name of the principal (user)
     */
    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}