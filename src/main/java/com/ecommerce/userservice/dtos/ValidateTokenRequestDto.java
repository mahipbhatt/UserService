package com.ecommerce.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for token validation requests.
 * Contains the necessary information to validate a user's token.
 * 
 * @author mahip.bhatt
 */
@Getter
@Setter
@AllArgsConstructor
public class ValidateTokenRequestDto {

    /**
     * The ID of the user whose token is being validated.
     */
    private Long userId;

    /**
     * The token to be validated.
     */
    private String token;
}