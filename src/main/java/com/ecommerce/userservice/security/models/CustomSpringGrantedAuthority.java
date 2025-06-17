package com.ecommerce.userservice.security.models;

import com.ecommerce.userservice.models.Role;
import org.springframework.security.core.GrantedAuthority;

/**
 * Custom implementation of {@link GrantedAuthority} to represent a user's role.
 *
 * @author mahip.bhatt
 */
public class CustomSpringGrantedAuthority implements GrantedAuthority {

    /**
     * The role associated with this authority.
     */
    private Role role;

    /**
     * Constructs a new {@code CustomSpringGrantedAuthority} with the specified role.
     *
     * @param role the role to associate with this authority
     */
    public CustomSpringGrantedAuthority(Role role) {
        this.role = role;
    }

    /**
     * Returns the authority granted to the user, which is the role name.
     *
     * @return the role name as a {@link String}
     */
    @Override
    public String getAuthority() {
        return role.getRole();
    }
}