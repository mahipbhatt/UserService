package com.ecommerce.userservice.controllers;

import com.ecommerce.userservice.dtos.*;
import com.ecommerce.userservice.models.SessionStatus;
import com.ecommerce.userservice.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link AuthController}.
 * This class tests the functionality of the AuthController methods using mocked dependencies.
 *
 * @author mahip.bhatt
 */
class AuthControllerTest {

    private AuthService authService;
    private AuthController authController;

    /**
     * Sets up the test environment by initializing mocked dependencies.
     */
    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        authController = new AuthController(authService);
    }

    /**
     * Tests the login functionality for a successful scenario.
     */
    @Test
    void testLoginSuccess() {
        LoginRequestDto request = new LoginRequestDto("test@example.com", "password");
        UserDto userDto = new UserDto("test@example.com", new HashSet<>());
        ResponseEntity<UserDto> expectedResponse = new ResponseEntity<>(userDto, HttpStatus.OK);

        when(authService.login(anyString(), anyString())).thenReturn(expectedResponse);

        ResponseEntity<UserDto> response = authController.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
        verify(authService, times(1)).login(request.getEmail(), request.getPassword());
    }

    /**
     * Tests the logout functionality for a successful scenario.
     */
    @Test
    void testLogoutSuccess() {
        LogoutRequestDto request = new LogoutRequestDto("token", 1L);
        ResponseEntity<Void> expectedResponse = ResponseEntity.ok().build();

        when(authService.logout(anyString(), anyLong())).thenReturn(expectedResponse);

        ResponseEntity<Void> response = authController.logout(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authService, times(1)).logout(request.getToken(), request.getUserId());
    }

    /**
     * Tests the sign-up functionality for a successful scenario.
     */
    @Test
    void testSignUpSuccess() {
        SignUpRequestDto request = new SignUpRequestDto("test@example.com", "password");
        UserDto userDto = new UserDto("test@example.com", Collections.emptySet());

        when(authService.signUp(anyString(), anyString())).thenReturn(userDto);

        ResponseEntity<UserDto> response = authController.signUp(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
        verify(authService, times(1)).signUp(request.getEmail(), request.getPassword());
    }

    /**
     * Tests the token validation functionality for a successful scenario.
     */
    @Test
    void testValidateTokenSuccess() {
        ValidateTokenRequestDto request = new ValidateTokenRequestDto(1L, "token");
        SessionStatus sessionStatus = SessionStatus.ACTIVE;

        when(authService.validate(anyString(), anyLong())).thenReturn(sessionStatus);

        ResponseEntity<SessionStatus> response = authController.validateToken(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionStatus, response.getBody());
        verify(authService, times(1)).validate(request.getToken(), request.getUserId());
    }
}