package com.ecommerce.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for user sign-up requests.
 * Contains the necessary information for registering a new user.
 *
 * @author mahip.bhatt
 */
@Getter
@Setter
@AllArgsConstructor
public class SignUpRequestDto {

    /**
     * The email address of the user signing up.
     */
    private String email;

    /**
     * The password of the user signing up.
     */
    private String password;
}
