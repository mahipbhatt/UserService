package com.ecommerce.userservice.security.repositories;

import java.util.Optional;

import com.ecommerce.userservice.security.models.Authorization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Authorization} entities.
 * Provides methods for retrieving authorizations based on various token types.
 * 
 * @author mahip.bhatt
 */
@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization, String> {

    /**
     * Finds an {@link Authorization} by its state.
     *
     * @param state the state of the authorization
     * @return an {@link Optional} containing the found {@link Authorization}, or empty if not found
     */
    Optional<Authorization> findByState(String state);

    /**
     * Finds an {@link Authorization} by its authorization code value.
     *
     * @param authorizationCode the authorization code
     * @return an {@link Optional} containing the found {@link Authorization}, or empty if not found
     */
    Optional<Authorization> findByAuthorizationCodeValue(String authorizationCode);

    /**
     * Finds an {@link Authorization} by its access token value.
     *
     * @param accessToken the access token
     * @return an {@link Optional} containing the found {@link Authorization}, or empty if not found
     */
    Optional<Authorization> findByAccessTokenValue(String accessToken);

    /**
     * Finds an {@link Authorization} by its refresh token value.
     *
     * @param refreshToken the refresh token
     * @return an {@link Optional} containing the found {@link Authorization}, or empty if not found
     */
    Optional<Authorization> findByRefreshTokenValue(String refreshToken);

    /**
     * Finds an {@link Authorization} by its OIDC ID token value.
     *
     * @param idToken the OIDC ID token
     * @return an {@link Optional} containing the found {@link Authorization}, or empty if not found
     */
    Optional<Authorization> findByOidcIdTokenValue(String idToken);

    /**
     * Finds an {@link Authorization} by its user code value.
     *
     * @param userCode the user code
     * @return an {@link Optional} containing the found {@link Authorization}, or empty if not found
     */
    Optional<Authorization> findByUserCodeValue(String userCode);

    /**
     * Finds an {@link Authorization} by its device code value.
     *
     * @param deviceCode the device code
     * @return an {@link Optional} containing the found {@link Authorization}, or empty if not found
     */
    Optional<Authorization> findByDeviceCodeValue(String deviceCode);

    /**
     * Finds an {@link Authorization} by matching any of the provided token types.
     *
     * @param token the token to search for
     * @return an {@link Optional} containing the found {@link Authorization}, or empty if not found
     */
    @Query("select a from Authorization a where a.state = :token" +
            " or a.authorizationCodeValue = :token" +
            " or a.accessTokenValue = :token" +
            " or a.refreshTokenValue = :token" +
            " or a.oidcIdTokenValue = :token" +
            " or a.userCodeValue = :token" +
            " or a.deviceCodeValue = :token")
    Optional<Authorization> findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValueOrOidcIdTokenValueOrUserCodeValueOrDeviceCodeValue(@Param("token") String token);
}