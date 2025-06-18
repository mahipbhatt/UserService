package com.ecommerce.userservice.security.services;

import com.ecommerce.userservice.security.models.AuthorizationConsent;
import com.ecommerce.userservice.security.repositories.AuthorizationConsentRepository;
import com.ecommerce.userservice.security.service.JpaOAuth2AuthorizationConsentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link JpaOAuth2AuthorizationConsentService}.
 * This class tests the functionality of the JpaOAuth2AuthorizationConsentService methods using mocked dependencies.
 *
 * @author mahip.bhatt
 */
class JpaOAuth2AuthorizationConsentServiceTest {

    private AuthorizationConsentRepository authorizationConsentRepository;
    private RegisteredClientRepository registeredClientRepository;
    private JpaOAuth2AuthorizationConsentService authorizationConsentService;

    /**
     * Sets up the test environment by initializing mocked dependencies.
     */
    @BeforeEach
    void setUp() {
        authorizationConsentRepository = mock(AuthorizationConsentRepository.class);
        registeredClientRepository = mock(RegisteredClientRepository.class);
        authorizationConsentService = new JpaOAuth2AuthorizationConsentService(authorizationConsentRepository, registeredClientRepository);
    }

    /**
     * Tests the save functionality for a successful scenario.
     */
    @Test
    void testSaveAuthorizationConsentSuccess() {
        // Arrange
        OAuth2AuthorizationConsent consent = OAuth2AuthorizationConsent.withId("clientId", "principalName")
                .authority(new SimpleGrantedAuthority("ROLE_USER"))
                .build();
        AuthorizationConsent entity = new AuthorizationConsent();
        when(authorizationConsentRepository.save(any(AuthorizationConsent.class))).thenReturn(entity);

        // Act
        authorizationConsentService.save(consent);

        // Assert
        verify(authorizationConsentRepository, times(1)).save(any(AuthorizationConsent.class));
    }

    /**
     * Tests the save functionality when the input is null.
     */
    @Test
    void testSaveAuthorizationConsentWithNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> authorizationConsentService.save(null));
    }

    /**
     * Tests the remove functionality for a successful scenario.
     */
    @Test
    void testRemoveAuthorizationConsentSuccess() {
        // Arrange
        AuthorizationConsent entity = new AuthorizationConsent();
        entity.setRegisteredClientId("clientId");
        entity.setPrincipalName("principalName");
        entity.setAuthorities("ROLE_USER");

        when(authorizationConsentRepository.findByRegisteredClientIdAndPrincipalName("clientId", "principalName"))
                .thenReturn(Optional.of(entity));

        OAuth2AuthorizationConsent consent = OAuth2AuthorizationConsent.withId("clientId", "principalName")
                .authority(new SimpleGrantedAuthority("ROLE_USER"))
                .build();

        // Act
        authorizationConsentService.remove(consent);

        // Assert
        verify(authorizationConsentRepository, times(1))
                .deleteByRegisteredClientIdAndPrincipalName("clientId", "principalName");
    }

    /**
     * Tests the remove functionality when the input is null.
     */
    @Test
    void testRemoveAuthorizationConsentWithNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> authorizationConsentService.remove(null));
    }

    /**
     * Tests the findById functionality for a successful scenario.
     */
    @Test
    void testFindByIdSuccess() {
        // Arrange
        String clientId = "clientId";
        String principalName = "principalName";
        AuthorizationConsent entity = new AuthorizationConsent();
        entity.setRegisteredClientId(clientId);
        entity.setPrincipalName(principalName);
        when(authorizationConsentRepository.findByRegisteredClientIdAndPrincipalName(clientId, principalName))
                .thenReturn(Optional.of(entity));
        RegisteredClient registeredClient = mock(RegisteredClient.class);
        when(registeredClientRepository.findById(clientId)).thenReturn(registeredClient);

        // Act
        OAuth2AuthorizationConsent consent = authorizationConsentService.findById(clientId, principalName);

        // Assert
        assertNotNull(consent);
        assertEquals(clientId, consent.getRegisteredClientId());
        assertEquals(principalName, consent.getPrincipalName());
        verify(authorizationConsentRepository, times(1))
                .findByRegisteredClientIdAndPrincipalName(clientId, principalName);
        verify(registeredClientRepository, times(1)).findById(clientId);
    }

    /**
     * Tests the findById functionality when the client ID is invalid.
     */
    @Test
    void testFindByIdWithInvalidClientId() {
        // Arrange
        String clientId = "invalidClientId";
        String principalName = "principalName";
        when(authorizationConsentRepository.findByRegisteredClientIdAndPrincipalName(clientId, principalName))
                .thenReturn(Optional.empty());

        // Act
        OAuth2AuthorizationConsent consent = authorizationConsentService.findById(clientId, principalName);

        // Assert
        assertNull(consent);
        verify(authorizationConsentRepository, times(1))
                .findByRegisteredClientIdAndPrincipalName(clientId, principalName);
    }

    /**
     * Tests the findById functionality when the registered client is missing.
     */
    @Test
    void testFindByIdWithMissingRegisteredClient() {
        // Arrange
        String clientId = "clientId";
        String principalName = "principalName";
        AuthorizationConsent entity = new AuthorizationConsent();
        entity.setRegisteredClientId(clientId);
        entity.setPrincipalName(principalName);
        when(authorizationConsentRepository.findByRegisteredClientIdAndPrincipalName(clientId, principalName))
                .thenReturn(Optional.of(entity));
        when(registeredClientRepository.findById(clientId)).thenReturn(null);

        // Act & Assert
        assertThrows(DataRetrievalFailureException.class, () -> authorizationConsentService.findById(clientId, principalName));
        verify(authorizationConsentRepository, times(1))
                .findByRegisteredClientIdAndPrincipalName(clientId, principalName);
        verify(registeredClientRepository, times(1)).findById(clientId);
    }
}