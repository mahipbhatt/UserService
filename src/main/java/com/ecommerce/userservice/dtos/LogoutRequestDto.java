package com.ecommerce.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for handling logout requests.
 * Contains the session token and user ID.
 *
 * @author mahip.bhatt
 */
@Getter
@Setter
public class LogoutRequestDto {

    private String token;
    private Long userId;

    // No-argument constructor for serialization/deserialization
    public LogoutRequestDto() {
    }

    // Constructor for initializing fields
    public LogoutRequestDto(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }
}