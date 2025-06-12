package com.example.userservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing a role in the application.
 * Extends {@link BaseModel} to inherit the ID field.
 * 
 * @author mahip.bhatt
 */
@Entity
@Getter
@Setter
public class Role extends BaseModel {

    /**
     * The name of the role.
     */
    private String role;
}