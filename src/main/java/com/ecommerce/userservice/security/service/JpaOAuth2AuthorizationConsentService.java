package com.ecommerce.userservice.security.service;

import java.util.HashSet;
import java.util.Set;

import com.ecommerce.userservice.security.models.AuthorizationConsent;
import com.ecommerce.userservice.security.repositories.AuthorizationConsentRepository;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Service implementation of {@link OAuth2AuthorizationConsentService} for managing
 * {@link OAuth2AuthorizationConsent} entities using JPA.
 * 
 * @author mahip.bhatt
 */
@Component
public class JpaOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {

    private final AuthorizationConsentRepository authorizationConsentRepository;
    private final RegisteredClientRepository registeredClientRepository;

    /**
     * Constructs a new {@code JpaOAuth2AuthorizationConsentService}.
     *
     * @param authorizationConsentRepository the repository for {@link AuthorizationConsent}
     * @param registeredClientRepository the repository for {@link RegisteredClient}
     */
    public JpaOAuth2AuthorizationConsentService(AuthorizationConsentRepository authorizationConsentRepository,
                                                RegisteredClientRepository registeredClientRepository) {
        Assert.notNull(authorizationConsentRepository, "authorizationConsentRepository cannot be null");
        Assert.notNull(registeredClientRepository, "registeredClientRepository cannot be null");
        this.authorizationConsentRepository = authorizationConsentRepository;
        this.registeredClientRepository = registeredClientRepository;
    }

    /**
     * Saves the given {@link OAuth2AuthorizationConsent}.
     *
     * @param authorizationConsent the authorization consent to save
     */
    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
        this.authorizationConsentRepository.save(toEntity(authorizationConsent));
    }

    /**
     * Removes the given {@link OAuth2AuthorizationConsent}.
     *
     * @param authorizationConsent the authorization consent to remove
     */
    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
        this.authorizationConsentRepository.deleteByRegisteredClientIdAndPrincipalName(
                authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
    }

    /**
     * Finds an {@link OAuth2AuthorizationConsent} by its registered client ID and principal name.
     *
     * @param registeredClientId the registered client ID
     * @param principalName the principal name
     * @return the found {@link OAuth2AuthorizationConsent}, or {@code null} if not found
     */
    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        Assert.hasText(registeredClientId, "registeredClientId cannot be empty");
        Assert.hasText(principalName, "principalName cannot be empty");
        return this.authorizationConsentRepository.findByRegisteredClientIdAndPrincipalName(
                registeredClientId, principalName).map(this::toObject).orElse(null);
    }

    /**
     * Converts an {@link AuthorizationConsent} entity to an {@link OAuth2AuthorizationConsent} object.
     *
     * @param authorizationConsent the entity to convert
     * @return the converted {@link OAuth2AuthorizationConsent}
     */
    private OAuth2AuthorizationConsent toObject(AuthorizationConsent authorizationConsent) {
        String registeredClientId = authorizationConsent.getRegisteredClientId();
        RegisteredClient registeredClient = this.registeredClientRepository.findById(registeredClientId);
        if (registeredClient == null) {
            throw new DataRetrievalFailureException(
                    "The RegisteredClient with id '" + registeredClientId + "' was not found in the RegisteredClientRepository.");
        }

        OAuth2AuthorizationConsent.Builder builder = OAuth2AuthorizationConsent.withId(
                registeredClientId, authorizationConsent.getPrincipalName());

    if (authorizationConsent.getAuthorities() != null && !authorizationConsent.getAuthorities().isEmpty()) {
            for (String authority : StringUtils.commaDelimitedListToSet(authorizationConsent.getAuthorities())) {
                builder.authority(new SimpleGrantedAuthority(authority));
            }
    } else {
        // Provide a default authority if none are present
        builder.authority(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return builder.build();
    }

    /**
     * Converts an {@link OAuth2AuthorizationConsent} object to an {@link AuthorizationConsent} entity.
     *
     * @param authorizationConsent the object to convert
     * @return the converted {@link AuthorizationConsent} entity
     */
    private AuthorizationConsent toEntity(OAuth2AuthorizationConsent authorizationConsent) {
        AuthorizationConsent entity = new AuthorizationConsent();
        entity.setRegisteredClientId(authorizationConsent.getRegisteredClientId());
        entity.setPrincipalName(authorizationConsent.getPrincipalName());

        Set<String> authorities = new HashSet<>();
        for (GrantedAuthority authority : authorizationConsent.getAuthorities()) {
            authorities.add(authority.getAuthority());
        }
        entity.setAuthorities(StringUtils.collectionToCommaDelimitedString(authorities));

        return entity;
    }
}