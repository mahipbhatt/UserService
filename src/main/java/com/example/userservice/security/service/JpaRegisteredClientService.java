package com.example.userservice.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.userservice.security.models.Client;
import com.example.userservice.security.repositories.ClientRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Service implementation for managing {@link RegisteredClient} entities using JPA.
 * 
 * @author mahip.bhatt
 */
@Component
public class JpaRegisteredClientService implements RegisteredClientRepository {

    private final ClientRepository clientRepository;
    private final ObjectMapper objectMapper;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Constructor to initialize the service with required dependencies.
     *
     * @param clientRepository the repository for managing {@link Client} entities
     */
    public JpaRegisteredClientService(ClientRepository clientRepository) {
        Assert.notNull(clientRepository, "clientRepository cannot be null");
        this.clientRepository = clientRepository;

        this.objectMapper = new ObjectMapper();
        ClassLoader classLoader = JpaRegisteredClientService.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        this.objectMapper.registerModules(securityModules);
        this.objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
    }

    /**
     * Saves a {@link RegisteredClient} entity.
     *
     * @param registeredClient the client to save
     */
    @Override
    public void save(RegisteredClient registeredClient) {
        Assert.notNull(registeredClient, "registeredClient cannot be null");
        this.clientRepository.save(toEntity(registeredClient));
    }

    /**
     * Finds a {@link RegisteredClient} by its ID.
     *
     * @param id the ID of the client
     * @return the found {@link RegisteredClient}, or null if not found
     */
    @Override
    public RegisteredClient findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return this.clientRepository.findById(id).map(this::toObject).orElse(null);
    }

    /**
     * Finds a {@link RegisteredClient} by its client ID.
     *
     * @param clientId the client ID
     * @return the found {@link RegisteredClient}, or null if not found
     */
    @Override
    public RegisteredClient findByClientId(String clientId) {
        Assert.hasText(clientId, "clientId cannot be empty");
        return this.clientRepository.findByClientId(clientId).map(this::toObject).orElse(null);
    }

    /**
     * Converts a {@link Client} entity to a {@link RegisteredClient}.
     *
     * @param client the entity to convert
     * @return the converted {@link RegisteredClient}
     */
    private RegisteredClient toObject(Client client) {
        Set<String> clientAuthenticationMethods = StringUtils.commaDelimitedListToSet(client.getClientAuthenticationMethods());
        Set<String> authorizationGrantTypes = StringUtils.commaDelimitedListToSet(client.getAuthorizationGrantTypes());
        Set<String> redirectUris = StringUtils.commaDelimitedListToSet(client.getRedirectUris());
        Set<String> postLogoutRedirectUris = StringUtils.commaDelimitedListToSet(client.getPostLogoutRedirectUris());
        Set<String> clientScopes = StringUtils.commaDelimitedListToSet(client.getScopes());

        RegisteredClient.Builder builder = RegisteredClient.withId(client.getId())
                .clientId(client.getClientId())
                .clientIdIssuedAt(client.getClientIdIssuedAt())
                .clientSecret(client.getClientSecret())
                .clientSecretExpiresAt(client.getClientSecretExpiresAt())
                .clientName(client.getClientName())
                .clientAuthenticationMethods(authMethods ->
                        clientAuthenticationMethods.forEach(method ->
                                authMethods.add(resolveClientAuthenticationMethod(method))))
                .authorizationGrantTypes(grantTypes ->
                        authorizationGrantTypes.forEach(grantType ->
                                grantTypes.add(resolveAuthorizationGrantType(grantType))))
                .redirectUris(redirectUris::addAll)
                .postLogoutRedirectUris(postLogoutRedirectUris::addAll)
                .scopes(clientScopes::addAll);

        builder.clientSettings(ClientSettings.withSettings(parseMap(client.getClientSettings())).build());
        builder.tokenSettings(TokenSettings.withSettings(parseMap(client.getTokenSettings())).build());

        return builder.build();
    }

    /**
     * Converts a {@link RegisteredClient} to a {@link Client} entity.
     *
     * @param registeredClient the client to convert
     * @return the converted {@link Client} entity
     */
    private Client toEntity(RegisteredClient registeredClient) {
        List<String> clientAuthenticationMethods = new ArrayList<>();
        registeredClient.getClientAuthenticationMethods().forEach(method ->
                clientAuthenticationMethods.add(method.getValue()));

        List<String> authorizationGrantTypes = new ArrayList<>();
        registeredClient.getAuthorizationGrantTypes().forEach(grantType ->
                authorizationGrantTypes.add(grantType.getValue()));

        Client entity = new Client();
        entity.setId(registeredClient.getId());
        entity.setClientId(registeredClient.getClientId());
        entity.setClientIdIssuedAt(registeredClient.getClientIdIssuedAt());
        entity.setClientSecret(registeredClient.getClientSecret());
        entity.setClientSecretExpiresAt(registeredClient.getClientSecretExpiresAt());
        entity.setClientName(registeredClient.getClientName());
        entity.setClientAuthenticationMethods(StringUtils.collectionToCommaDelimitedString(clientAuthenticationMethods));
        entity.setAuthorizationGrantTypes(StringUtils.collectionToCommaDelimitedString(authorizationGrantTypes));
        entity.setRedirectUris(StringUtils.collectionToCommaDelimitedString(registeredClient.getRedirectUris()));
        entity.setPostLogoutRedirectUris(StringUtils.collectionToCommaDelimitedString(registeredClient.getPostLogoutRedirectUris()));
        entity.setScopes(StringUtils.collectionToCommaDelimitedString(registeredClient.getScopes()));
        entity.setClientSettings(writeMap(registeredClient.getClientSettings().getSettings()));
        entity.setTokenSettings(writeMap(registeredClient.getTokenSettings().getSettings()));

        return entity;
    }

    /**
     * Parses a JSON string into a {@link Map}.
     *
     * @param data the JSON string
     * @return the parsed {@link Map}
     */
    private Map<String, Object> parseMap(String data) {
        try {
            return this.objectMapper.readValue(data, new TypeReference<Map<String, Object>>() {});
        } catch (Exception ex) {
            throw new IllegalArgumentException("Failed to parse JSON: " + ex.getMessage(), ex);
        }
    }

    /**
     * Converts a {@link Map} into a JSON string.
     *
     * @param data the map to convert
     * @return the JSON string
     */
    private String writeMap(Map<String, Object> data) {
        try {
            return this.objectMapper.writeValueAsString(data);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Failed to write JSON: " + ex.getMessage(), ex);
        }
    }

    /**
     * Resolves an {@link AuthorizationGrantType} from its string value.
     *
     * @param authorizationGrantType the string value
     * @return the resolved {@link AuthorizationGrantType}
     */
    private static AuthorizationGrantType resolveAuthorizationGrantType(String authorizationGrantType) {
        if (AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.AUTHORIZATION_CODE;
        } else if (AuthorizationGrantType.CLIENT_CREDENTIALS.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.CLIENT_CREDENTIALS;
        } else if (AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.REFRESH_TOKEN;
        }
        return new AuthorizationGrantType(authorizationGrantType); // Custom grant type
    }

    /**
     * Resolves a {@link ClientAuthenticationMethod} from its string value.
     *
     * @param clientAuthenticationMethod the string value
     * @return the resolved {@link ClientAuthenticationMethod}
     */
    private static ClientAuthenticationMethod resolveClientAuthenticationMethod(String clientAuthenticationMethod) {
        if (ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue().equals(clientAuthenticationMethod)) {
            return ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
        } else if (ClientAuthenticationMethod.CLIENT_SECRET_POST.getValue().equals(clientAuthenticationMethod)) {
            return ClientAuthenticationMethod.CLIENT_SECRET_POST;
        } else if (ClientAuthenticationMethod.NONE.getValue().equals(clientAuthenticationMethod)) {
            return ClientAuthenticationMethod.NONE;
        }
        return new ClientAuthenticationMethod(clientAuthenticationMethod); // Custom authentication method
    }
}