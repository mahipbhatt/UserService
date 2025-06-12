package com.example.userservice.models;

import com.example.userservice.dtos.UserDto;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a user in the application.
 * Extends {@link BaseModel} to inherit the ID field.
 * 
 * @author mahip.bhatt
 */
@Entity
@Getter
@Setter
public class User extends BaseModel {

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The roles assigned to the user.
     * Represents a many-to-many relationship with the {@link Role} entity.
     */
    @ManyToMany
    private Set<Role> roles = new HashSet<>();

    /**
     * Converts a {@link User} entity to a {@link UserDto}.
     *
     * @param user the user entity to convert
     * @return the corresponding {@link UserDto} object
     */
    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}