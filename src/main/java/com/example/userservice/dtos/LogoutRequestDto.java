package com.example.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for user logout requests.
 * Contains the necessary information to log out a user.
 *
 * @author mahip.bhatt
 */
@Getter
@Setter
public class LogoutRequestDto {

    /**
     * The authentication token of the user.
     */
    private String token;

    /**
     * The ID of the user attempting to log out.
     */
    private Long userId;
}