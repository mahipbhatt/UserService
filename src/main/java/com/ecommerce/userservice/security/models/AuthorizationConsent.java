package com.ecommerce.userservice.security.models;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * Entity representing user consent for a registered client.
 * Used to store the authorities (scopes) granted by the user to the client.
 * 
 * @author mahip.bhatt
 */
@Entity
@Table(name = "`authorizationConsent`")
@IdClass(AuthorizationConsent.AuthorizationConsentId.class)
public class AuthorizationConsent {

    /**
     * The ID of the registered client requesting authorization.
     */
    @Id
    private String registeredClientId;

    /**
     * The name of the principal (user) granting consent.
     */
    @Id
    private String principalName;

    /**
     * The authorities (scopes) granted by the user to the client.
     */
    @Column(length = 1000)
    private String authorities;

    /**
     * Gets the registered client ID.
     *
     * @return the registered client ID
     */
    public String getRegisteredClientId() {
        return registeredClientId;
    }

    /**
     * Sets the registered client ID.
     *
     * @param registeredClientId the registered client ID
     */
    public void setRegisteredClientId(String registeredClientId) {
        this.registeredClientId = registeredClientId;
    }

    /**
     * Gets the principal name.
     *
     * @return the principal name
     */
    public String getPrincipalName() {
        return principalName;
    }

    /**
     * Sets the principal name.
     *
     * @param principalName the principal name
     */
    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    /**
     * Gets the authorities granted by the user.
     *
     * @return the authorities
     */
    public String getAuthorities() {
        return authorities;
    }

    /**
     * Sets the authorities granted by the user.
     *
     * @param authorities the authorities
     */
    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    /**
     * Composite key class for {@link AuthorizationConsent}.
     */
    public static class AuthorizationConsentId implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * The ID of the registered client.
         */
        private String registeredClientId;

        /**
         * The name of the principal (user).
         */
        private String principalName;

        /**
         * Gets the registered client ID.
         *
         * @return the registered client ID
         */
        public String getRegisteredClientId() {
            return registeredClientId;
        }

        /**
         * Sets the registered client ID.
         *
         * @param registeredClientId the registered client ID
         */
        public void setRegisteredClientId(String registeredClientId) {
            this.registeredClientId = registeredClientId;
        }

        /**
         * Gets the principal name.
         *
         * @return the principal name
         */
        public String getPrincipalName() {
            return principalName;
        }

        /**
         * Sets the principal name.
         *
         * @param principalName the principal name
         */
        public void setPrincipalName(String principalName) {
            this.principalName = principalName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AuthorizationConsentId that = (AuthorizationConsentId) o;
            return registeredClientId.equals(that.registeredClientId) && principalName.equals(that.principalName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(registeredClientId, principalName);
        }
    }
}