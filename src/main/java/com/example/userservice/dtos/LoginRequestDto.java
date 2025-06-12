package com.example.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for user login requests.
 * Contains the necessary information for user authentication.
 * 
 * @author mahip.bhatt
 */
@Getter
@Setter
public class LoginRequestDto {

    /**
     * The email address of the user attempting to log in.
     */
    private String email;

    /**
     * The password of the user attempting to log in.
     */
    private String password;
}