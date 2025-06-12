package com.example.userservice.dtos;

import com.example.userservice.models.Role;
import com.example.userservice.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Data Transfer Object for user details.
 * Contains the user's email and associated roles.
 * 
 * @author mahip.bhatt
 */
@Getter
@Setter
public class UserDto {

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The set of roles assigned to the user.
     */
    private Set<Role> roles = new HashSet<>();

    /**
     * Converts a {@link User} entity to a {@link UserDto}.
     *
     * @param user The {@link User} entity to be converted.
     * @return A {@link UserDto} containing the user's details.
     */
    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());

        return userDto;
    }
}