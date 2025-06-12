package com.example.userservice.security.models;

import java.time.Instant;
import jakarta.persistence.*;

/**
 * Entity representing a client in the authorization system.
 * Stores details about the client, including authentication methods, grant types, and settings.
 * 
 * @author mahip.bhatt
 */
@Entity
@Table(name = "client")
public class Client {

    /**
     * Unique identifier for the client.
     */
    @Id
    private String id;

    /**
     * Unique client ID used for authentication.
     */
    @Column(name = "client_id", nullable = false, unique = true)
    private String clientId;

    /**
     * Timestamp when the client ID was issued.
     */
    @Column(name = "client_id_issued_at", nullable = false)
    private Instant clientIdIssuedAt;

    /**
     * Secret associated with the client for authentication.
     */
    @Column(name = "client_secret")
    private String clientSecret;

    /**
     * Timestamp when the client secret expires.
     */
    @Column(name = "client_secret_expires_at")
    private Instant clientSecretExpiresAt;

    /**
     * Name of the client.
     */
    @Column(name = "client_name", nullable = false)
    private String clientName;

    /**
     * Authentication methods supported by the client.
     */
    @Column(name = "client_authentication_methods", nullable = false)
    private String clientAuthenticationMethods;

    /**
     * Authorization grant types supported by the client.
     */
    @Column(name = "authorization_grant_types", nullable = false)
    private String authorizationGrantTypes;

    /**
     * Redirect URIs for the client.
     */
    @Column(name = "redirect_uris")
    private String redirectUris;

    /**
     * Post-logout redirect URIs for the client.
     */
    @Column(name = "post_logout_redirect_uris")
    private String postLogoutRedirectUris;

    /**
     * Scopes available to the client.
     */
    @Column(name = "scopes", nullable = false)
    private String scopes;

    /**
     * Client-specific settings.
     */
    @Column(name = "client_settings", nullable = false)
    private String clientSettings;

    /**
     * Token-specific settings for the client.
     */
    @Column(name = "token_settings", columnDefinition = "TEXT", nullable = true)
    private String tokenSettings;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Instant getClientIdIssuedAt() {
        return clientIdIssuedAt;
    }

    public void setClientIdIssuedAt(Instant clientIdIssuedAt) {
        this.clientIdIssuedAt = clientIdIssuedAt;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Instant getClientSecretExpiresAt() {
        return clientSecretExpiresAt;
    }

    public void setClientSecretExpiresAt(Instant clientSecretExpiresAt) {
        this.clientSecretExpiresAt = clientSecretExpiresAt;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAuthenticationMethods() {
        return clientAuthenticationMethods;
    }

    public void setClientAuthenticationMethods(String clientAuthenticationMethods) {
        this.clientAuthenticationMethods = clientAuthenticationMethods;
    }

    public String getAuthorizationGrantTypes() {
        return authorizationGrantTypes;
    }

    public void setAuthorizationGrantTypes(String authorizationGrantTypes) {
        this.authorizationGrantTypes = authorizationGrantTypes;
    }

    public String getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(String redirectUris) {
        this.redirectUris = redirectUris;
    }

    public String getPostLogoutRedirectUris() {
        return postLogoutRedirectUris;
    }

    public void setPostLogoutRedirectUris(String postLogoutRedirectUris) {
        this.postLogoutRedirectUris = postLogoutRedirectUris;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public String getClientSettings() {
        return clientSettings;
    }

    public void setClientSettings(String clientSettings) {
        this.clientSettings = clientSettings;
    }

    public String getTokenSettings() {
        return tokenSettings;
    }

    public void setTokenSettings(String tokenSettings) {
        this.tokenSettings = tokenSettings;
    }
}