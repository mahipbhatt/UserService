package com.example.userservice.security.models;

import com.example.userservice.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Custom implementation of {@link UserDetails} to integrate the application's {@link User} model
 * with Spring Security.
 *
 * @author mahip.bhatt
 */
public class CustomSpringUserDetails implements UserDetails {

    /**
     * The user entity associated with this user details object.
     */
    private final User user;

    /**
     * Constructs a new {@code CustomSpringUserDetails} with the specified user.
     *
     * @param user the user entity
     */
    public CustomSpringUserDetails(User user) {
        this.user = user;
    }

    /**
     * Returns the authorities granted to the user.
     *
     * @return a collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // Replace with actual logic to fetch user authorities
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the user's password
     */
    @Override
    public String getPassword() {
        return null; // Replace with actual logic to fetch user password
    }

    /**
     * Returns the username used to authenticate the user.
     *
     * @return the user's username
     */
    @Override
    public String getUsername() {
        return null; // Replace with actual logic to fetch user username
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return {@code true} if the account is non-expired, {@code false} otherwise
     */
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    /**
     * Indicates whether the user is locked or unlocked.
     *
     * @return {@code true} if the account is non-locked, {@code false} otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     *
     * @return {@code true} if the credentials are non-expired, {@code false} otherwise
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    /**
     * Indicates whether the user is enabled or disabled.
     *
     * @return {@code true} if the user is enabled, {@code false} otherwise
     */
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}