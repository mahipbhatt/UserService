package com.ecommerce.userservice.security.service;

import com.ecommerce.userservice.security.models.Client;
import com.ecommerce.userservice.security.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JpaRegisteredClientServiceTest {

    private ClientRepository clientRepository;
    private JpaRegisteredClientService registeredClientService;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        registeredClientService = new JpaRegisteredClientService(clientRepository);
    }

    @Test
    void testSaveRegisteredClientSuccess() {
        // Arrange
        RegisteredClient registeredClient = RegisteredClient.withId("clientId")
                .clientId("testClientId")
                .clientSecret("testSecret")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // Add valid grant type
                .build();
        Client clientEntity = new Client();
        when(clientRepository.save(any(Client.class))).thenReturn(clientEntity);

        // Act
        registeredClientService.save(registeredClient);

        // Assert
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void testFindByIdSuccess() {
        // Arrange
        String clientId = "clientId";
        Client clientEntity = new Client();
        clientEntity.setId(clientId);
        clientEntity.setClientSettings("{\"requireAuthorizationConsent\":true}"); // Add valid client settings
        clientEntity.setTokenSettings("{\"accessTokenTimeToLive\":3600}"); // Add valid token settings
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(clientEntity));

        // Act
        RegisteredClient registeredClient = registeredClientService.findById(clientId);

        // Assert
        assertNotNull(registeredClient);
        assertEquals(clientId, registeredClient.getId());
        verify(clientRepository, times(1)).findById(clientId);
    }

    @Test
    void testFindByClientIdSuccess() {
        // Arrange
        String clientId = "testClientId";
        Client clientEntity = new Client();
        clientEntity.setId("clientId"); // Add valid ID
        clientEntity.setClientId(clientId);
        clientEntity.setClientSettings("{\"requireAuthorizationConsent\":true}"); // Add valid client settings
        clientEntity.setTokenSettings("{\"accessTokenTimeToLive\":3600}"); // Add valid token settings
        when(clientRepository.findByClientId(clientId)).thenReturn(Optional.of(clientEntity));

        // Act
        RegisteredClient registeredClient = registeredClientService.findByClientId(clientId);

        // Assert
        assertNotNull(registeredClient);
        assertEquals(clientId, registeredClient.getClientId());
        verify(clientRepository, times(1)).findByClientId(clientId);
    }


    @Test
    void testSaveRegisteredClientWithNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> registeredClientService.save(null));
    }


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

    @Test
    void testFindByIdWithEmptyId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> registeredClientService.findById(""));
    }



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

    @Test
    void testFindByClientIdWithEmptyClientId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> registeredClientService.findByClientId(""));
    }
}