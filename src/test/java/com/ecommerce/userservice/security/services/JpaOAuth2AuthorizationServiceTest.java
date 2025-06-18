package com.ecommerce.userservice.security.services;

import com.ecommerce.userservice.security.models.Authorization;
import com.ecommerce.userservice.security.repositories.AuthorizationRepository;
import com.ecommerce.userservice.security.service.JpaOAuth2AuthorizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link JpaOAuth2AuthorizationService}.
 * This class tests the functionality of the JpaOAuth2AuthorizationService methods using mocked dependencies.
 *
 * @author mahip.bhatt
 */
class JpaOAuth2AuthorizationServiceTest {

    private AuthorizationRepository authorizationRepository;
    private RegisteredClientRepository registeredClientRepository;
    private JpaOAuth2AuthorizationService authorizationService;

    /**
     * Sets up the test environment by initializing mocked dependencies.
     */
    @BeforeEach
    void setUp() {
        authorizationRepository = mock(AuthorizationRepository.class);
        registeredClientRepository = mock(RegisteredClientRepository.class);
        authorizationService = new JpaOAuth2AuthorizationService(authorizationRepository, registeredClientRepository);
    }

    /**
     * Tests the findById functionality with a valid authorization grant type.
     */
    @Test
    void testFindByIdWithValidAuthorizationGrantType() {
        // Arrange
        String id = "authId";
        Authorization entity = new Authorization();
        entity.setId(id);
        entity.setPrincipalName("testPrincipal");
        entity.setAuthorizationGrantType("authorization_code");
        when(authorizationRepository.findById(id)).thenReturn(Optional.of(entity));
        RegisteredClient registeredClient = mock(RegisteredClient.class);
        when(registeredClientRepository.findById(entity.getRegisteredClientId())).thenReturn(registeredClient);

        // Act
        OAuth2Authorization authorization = authorizationService.findById(id);

        // Assert
        assertNotNull(authorization);
        assertEquals(id, authorization.getId());
        verify(authorizationRepository, times(1)).findById(id);
    }

    /**
     * Tests the save functionality when the input is null.
     */
    @Test
    void testSaveAuthorizationWithNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> authorizationService.save(null));
    }

    /**
     * Tests the remove functionality for a successful scenario.
     */
    @Test
    void testRemoveAuthorizationSuccess() {
        // Arrange
        OAuth2Authorization authorization = mock(OAuth2Authorization.class);
        when(authorization.getId()).thenReturn("authId");

        // Act
        authorizationService.remove(authorization);

        // Assert
        verify(authorizationRepository, times(1)).deleteById("authId");
    }

    /**
     * Tests the remove functionality when the input is null.
     */
    @Test
    void testRemoveAuthorizationWithNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> authorizationService.remove(null));
    }

    /**
     * Tests the findById functionality for a successful scenario.
     */
    @Test
    void testFindByIdSuccess() {
        // Arrange
        String id = "authId";
        Authorization entity = new Authorization();
        entity.setId(id);
        entity.setPrincipalName("testPrincipal");
        entity.setAuthorizationGrantType("authorization_code");
        when(authorizationRepository.findById(id)).thenReturn(Optional.of(entity));
        RegisteredClient registeredClient = mock(RegisteredClient.class);
        when(registeredClientRepository.findById(entity.getRegisteredClientId())).thenReturn(registeredClient);

        // Act
        OAuth2Authorization authorization = authorizationService.findById(id);

        // Assert
        assertNotNull(authorization);
        assertEquals(id, authorization.getId());
        verify(authorizationRepository, times(1)).findById(id);
    }

    /**
     * Tests the findById functionality with an invalid ID.
     */
    @Test
    void testFindByIdWithInvalidId() {
        // Arrange
        String id = "invalidId";
        when(authorizationRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        OAuth2Authorization authorization = authorizationService.findById(id);

        // Assert
        assertNull(authorization);
        verify(authorizationRepository, times(1)).findById(id);
    }

    /**
     * Tests the findById functionality when the registered client is missing.
     */
    @Test
    void testFindByIdWithMissingRegisteredClient() {
        // Arrange
        String id = "authId";
        Authorization entity = new Authorization();
        entity.setId(id);
        when(authorizationRepository.findById(id)).thenReturn(Optional.of(entity));
        when(registeredClientRepository.findById(entity.getRegisteredClientId())).thenReturn(null);

        // Act & Assert
        assertThrows(DataRetrievalFailureException.class, () -> authorizationService.findById(id));
        verify(authorizationRepository, times(1)).findById(id);
    }

    /**
     * Tests the findByToken functionality for a successful scenario.
     */
    @Test
    void testFindByTokenSuccess() {
        // Arrange
        String token = "accessToken";
        Authorization entity = new Authorization();
        entity.setPrincipalName("testPrincipal");
        entity.setAuthorizationGrantType("authorization_code");
        when(authorizationRepository.findByAccessTokenValue(token)).thenReturn(Optional.of(entity));
        RegisteredClient registeredClient = mock(RegisteredClient.class);
        when(registeredClientRepository.findById(entity.getRegisteredClientId())).thenReturn(registeredClient);

        // Act
        OAuth2Authorization authorization = authorizationService.findByToken(token, new OAuth2TokenType("access_token"));

        // Assert
        assertNotNull(authorization);
        verify(authorizationRepository, times(1)).findByAccessTokenValue(token);
    }

    /**
     * Tests the findByToken functionality with an invalid token.
     */
    @Test
    void testFindByTokenWithInvalidToken() {
        // Arrange
        String token = "invalidToken";
        when(authorizationRepository.findByAccessTokenValue(token)).thenReturn(Optional.empty());

        // Act
        OAuth2Authorization authorization = authorizationService.findByToken(token, new OAuth2TokenType("access_token"));

        // Assert
        assertNull(authorization);
        verify(authorizationRepository, times(1)).findByAccessTokenValue(token);
    }

    /**
     * Tests the findByToken functionality with a valid authorization grant type.
     */
    @Test
    void testFindByTokenWithValidAuthorizationGrantType() {
        // Arrange
        String token = "accessToken";
        Authorization entity = new Authorization();
        entity.setPrincipalName("testPrincipal");
        entity.setAuthorizationGrantType("authorization_code");
        when(authorizationRepository.findByAccessTokenValue(token)).thenReturn(Optional.of(entity));
        RegisteredClient registeredClient = mock(RegisteredClient.class);
        when(registeredClientRepository.findById(entity.getRegisteredClientId())).thenReturn(registeredClient);

        // Act
        OAuth2Authorization authorization = authorizationService.findByToken(token, new OAuth2TokenType("access_token"));

        // Assert
        assertNotNull(authorization);
        verify(authorizationRepository, times(1)).findByAccessTokenValue(token);
    }
}