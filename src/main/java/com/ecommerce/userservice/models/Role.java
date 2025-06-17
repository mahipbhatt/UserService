package com.ecommerce.userservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a role in the application.
 * Contains the name of the role.
 *
 * @author mahip.bhatt
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    /**
     * Constructor for creating a role with a specific name.
     *
     * @param role the name of the role
     */
    public Role(String role) {
        this.role = role;
    }
}