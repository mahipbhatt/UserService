package com.ecommerce.userservice.security.services;

import com.ecommerce.userservice.security.models.Client;
import com.ecommerce.userservice.security.repositories.ClientRepository;
import com.ecommerce.userservice.security.service.JpaRegisteredClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link JpaRegisteredClientService}.
 * This class tests the functionality of the JpaRegisteredClientService methods using mocked dependencies.
 *
 * @author mahip.bhatt
 */
class JpaRegisteredClientServiceTest {

    private ClientRepository clientRepository;
    private JpaRegisteredClientService registeredClientService;

    /**
     * Sets up the test environment by initializing mocked dependencies.
     */
    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        registeredClientService = new JpaRegisteredClientService(clientRepository);
    }

    /**
     * Tests the save functionality for a successful scenario.
     */
    @Test
    void testSaveRegisteredClientSuccess() {
        // Arrange
        RegisteredClient registeredClient = RegisteredClient.withId("clientId")
                .clientId("testClientId")
                .clientSecret("testSecret")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://localhost:8080/callback")
                .build();
        Client clientEntity = new Client();
        when(clientRepository.save(any(Client.class))).thenReturn(clientEntity);

        // Act
        registeredClientService.save(registeredClient);

        // Assert
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    /**
     * Tests the findById functionality for a successful scenario.
     */
    @Test
    void testFindByIdSuccess() {
        // Arrange
        String clientId = "testClientId";
        Client clientEntity = new Client();
        clientEntity.setId("clientId");
        clientEntity.setClientId(clientId);
        clientEntity.setAuthorizationGrantTypes("authorization_code,refresh_token");
        clientEntity.setRedirectUris("http://localhost:8080/callback");
        clientEntity.setClientSettings("{\"requireAuthorizationConsent\":true}");
        clientEntity.setTokenSettings("{\"accessTokenTimeToLive\":3600}");
        when(clientRepository.findById("clientId")).thenReturn(Optional.of(clientEntity));

        // Act
        RegisteredClient registeredClient = registeredClientService.findById("clientId");

        // Assert
        assertNotNull(registeredClient);
        assertEquals(clientId, registeredClient.getClientId());
        verify(clientRepository, times(1)).findById("clientId");
    }

    /**
     * Tests the findByClientId functionality for a successful scenario.
     */
    @Test
    void testFindByClientIdSuccess() {
        // Arrange
        String clientId = "testClientId";
        Client clientEntity = new Client();
        clientEntity.setId("clientId");
        clientEntity.setClientId(clientId);
        clientEntity.setAuthorizationGrantTypes("authorization_code,refresh_token");
        clientEntity.setRedirectUris("http://localhost:8080/callback");
        clientEntity.setClientSettings("{\"requireAuthorizationConsent\":true}");
        clientEntity.setTokenSettings("{\"accessTokenTimeToLive\":3600}");
        when(clientRepository.findByClientId(clientId)).thenReturn(Optional.of(clientEntity));

        // Act
        RegisteredClient registeredClient = registeredClientService.findByClientId(clientId);

        // Assert
        assertNotNull(registeredClient);
        assertEquals(clientId, registeredClient.getClientId());
        verify(clientRepository, times(1)).findByClientId(clientId);
    }

    /**
     * Tests the save functionality when the input is null.
     */
    @Test
    void testSaveRegisteredClientWithNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> registeredClientService.save(null));
    }

    /**
     * Tests the findById functionality with an invalid ID.
     */
    @Test
    void testFindByIdWithInvalidId() {
        // Arrange
        String clientId = "invalidClientId";
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // Act
        RegisteredClient registeredClient = registeredClientService.findById(clientId);

        // Assert
        assertNull(registeredClient);
        verify(clientRepository, times(1)).findById(clientId);
    }

    /**
     * Tests the findById functionality when the input ID is empty.
     */
    @Test
    void testFindByIdWithEmptyId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> registeredClientService.findById(""));
    }

    /**
     * Tests the findByClientId functionality with an invalid client ID.
     */
    @Test
    void testFindByClientIdWithInvalidClientId() {
        // Arrange
        String clientId = "invalidClientId";
        when(clientRepository.findByClientId(clientId)).thenReturn(Optional.empty());

        // Act
        RegisteredClient registeredClient = registeredClientService.findByClientId(clientId);

        // Assert
        assertNull(registeredClient);
        verify(clientRepository, times(1)).findByClientId(clientId);
    }

    /**
     * Tests the findByClientId functionality when the input client ID is empty.
     */
    @Test
    void testFindByClientIdWithEmptyClientId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> registeredClientService.findByClientId(""));
    }
}