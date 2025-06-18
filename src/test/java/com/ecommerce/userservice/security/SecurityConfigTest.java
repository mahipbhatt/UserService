package com.ecommerce.userservice.security;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for {@link SecurityConfig}.
 * This class tests the functionality of the SecurityConfig methods.
 *
 * @author mahip.bhatt
 */
class SecurityConfigTest {

    private SecurityConfig securityConfig;

    /**
     * Sets up the test environment by initializing the SecurityConfig instance.
     */
    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig();
    }

    /**
     * Tests the JWK source configuration.
     */
    @Test
    void testJwkSource() {
        // Act
        JWKSource<SecurityContext> jwkSource = securityConfig.jwkSource();

        // Assert
        assertNotNull(jwkSource);
    }

    /**
     * Tests the JWT decoder configuration.
     */
    @Test
    void testJwtDecoder() {
        // Act
        JwtDecoder jwtDecoder = securityConfig.jwtDecoder();

        // Assert
        assertNotNull(jwtDecoder);
    }

    /**
     * Tests the authorization server settings configuration.
     */
    @Test
    void testAuthorizationServerSettings() {
        // Act
        AuthorizationServerSettings settings = securityConfig.authorizationServerSettings();

        // Assert
        assertNotNull(settings);
        assertEquals("http://localhost:9000", settings.getIssuer());
    }
}