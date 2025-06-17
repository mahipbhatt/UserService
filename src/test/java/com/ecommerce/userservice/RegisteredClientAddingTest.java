package com.ecommerce.userservice;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

/**
 * Test class for adding a registered client to the database.
 * 
 * @author mahip.bhatt
 */
@SpringBootTest
public class RegisteredClientAddingTest {

    @Autowired
    private RegisteredClientRepository registeredClientRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Test method for adding a new registered client to the database.
     * This method is disabled by default and requires manual activation.
     */
    //@Test
    @Commit
    @Transactional
    @Disabled
    void testAddClient() {
        // Verify if the provided secret matches the stored hash
        boolean isMatch = bCryptPasswordEncoder.matches("secret", "$2a$10$ElG7PoSwUnV72MWTElG85OSw46eXhDODjn2QAHFDWk4BVX/ZntCBS");
        System.out.println("Does 'secret' match stored hash?: " + isMatch);

        // Create a new RegisteredClient object
        RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("oidc-client")
                .clientSecret(bCryptPasswordEncoder.encode("secret"))
                .clientAuthenticationMethods(authMethods -> authMethods.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC))
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/oidc-client")
                .redirectUri("http://localhost:8080/callback")
                .postLogoutRedirectUri("http://127.0.0.1:8080/")
                .scope(OidcScopes.PROFILE)
                .scope(OidcScopes.OPENID)
                .clientIdIssuedAt(Instant.now())
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

        // Save the client to the database
        registeredClientRepository.save(client);
    }
}