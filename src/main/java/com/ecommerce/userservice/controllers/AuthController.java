package com.ecommerce.userservice.controllers;

import com.ecommerce.userservice.dtos.*;
import com.ecommerce.userservice.models.SessionStatus;
import com.ecommerce.userservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling authentication-related operations.
 * Provides endpoints for login, logout, signup, and token validation.
 * 
 * @author mahip.bhatt
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Constructor-based dependency injection for AuthService.
     *
     * @param authService The authentication service.
     */
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint for user login.
     *
     * @param request The login request containing email and password.
     * @return ResponseEntity containing the user details and authentication token.
     */
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request) {
        return authService.login(request.getEmail(), request.getPassword());
    }

    /**
     * Endpoint for user logout.
     *
     * @param request The logout request containing token and user ID.
     * @return ResponseEntity indicating the logout status.
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto request) {
        return authService.logout(request.getToken(), request.getUserId());
    }

    /**
     * Endpoint for user signup.
     *
     * @param request The signup request containing email and password.
     * @return ResponseEntity containing the created user details.
     */
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto request) {
        UserDto userDto = authService.signUp(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(userDto);
    }

    /**
     * Endpoint for validating a user's token.
     *
     * @param request The token validation request containing token and user ID.
     * @return ResponseEntity containing the session status.
     */
    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validateToken(@RequestBody ValidateTokenRequestDto request) {
        SessionStatus sessionStatus = authService.validate(request.getToken(), request.getUserId());
        return ResponseEntity.ok(sessionStatus);
    }

}
