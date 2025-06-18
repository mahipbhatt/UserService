package com.ecommerce.userservice.services;

import com.ecommerce.userservice.dtos.UserDto;
import com.ecommerce.userservice.models.Session;
import com.ecommerce.userservice.models.SessionStatus;
import com.ecommerce.userservice.models.User;
import com.ecommerce.userservice.repositories.SessionRepository;
import com.ecommerce.userservice.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link AuthService}.
 * This class tests the functionality of the AuthService methods using mocked dependencies.
 *
 * @author mahip.bhatt
 */
class AuthServiceTest {

    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthService authService;

    /**
     * Sets up the test environment by initializing mocked dependencies.
     */
    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        sessionRepository = mock(SessionRepository.class);
        bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
        authService = new AuthService(userRepository, sessionRepository, bCryptPasswordEncoder);
    }

    /**
     * Tests the login functionality for a successful scenario.
     */
    @Test
    void testLoginSuccess() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.matches(password, user.getPassword())).thenReturn(true);

        // Act
        ResponseEntity<UserDto> response = authService.login(email, password);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getHeaders().get(HttpHeaders.SET_COOKIE));
        assertNotNull(response.getBody());
        verify(sessionRepository, times(1)).save(any(Session.class));
    }

    /**
     * Tests the login functionality for invalid credentials.
     */
    @Test
    void testLoginFailureInvalidCredentials() {
        // Arrange
        String email = "test@example.com";
        String password = "wrongPassword";
        User user = new User();
        user.setEmail(email);
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.matches(password, user.getPassword())).thenReturn(false);

        // Act
        ResponseEntity<UserDto> response = authService.login(email, password);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(sessionRepository, never()).save(any(Session.class));
    }

    /**
     * Tests the login functionality when the user is not found.
     */
    @Test
    void testLoginFailureUserNotFound() {
        // Arrange
        String email = "test@example.com";
        String password = "password";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<UserDto> response = authService.login(email, password);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(sessionRepository, never()).save(any(Session.class));
    }

    /**
     * Tests the logout functionality for a successful scenario.
     */
    @Test
    void testLogoutSuccess() {
        // Arrange
        String token = "validToken";
        Long userId = 1L;
        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);

        when(sessionRepository.findByTokenAndUser_Id(token, userId)).thenReturn(Optional.of(session));

        // Act
        ResponseEntity<Void> response = authService.logout(token, userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sessionRepository, times(1)).save(session);
    }

    /**
     * Tests the logout functionality when the session is not found.
     */
    @Test
    void testLogoutFailureSessionNotFound() {
        // Arrange
        String token = "invalidToken";
        Long userId = 1L;

        when(sessionRepository.findByTokenAndUser_Id(token, userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = authService.logout(token, userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(sessionRepository, never()).save(any(Session.class));
    }

    /**
     * Tests the sign-up functionality for a successful scenario.
     */
    @Test
    void testSignUpSuccess() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setEmail(email);
        user.setPassword("encodedPassword");

        when(bCryptPasswordEncoder.encode(password)).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserDto userDto = authService.signUp(email, password);

        // Assert
        assertNotNull(userDto);
        assertEquals(email, userDto.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    /**
     * Tests the token validation functionality when the session is not found.
     */
    @Test
    void testValidateTokenFailureSessionNotFound() {
        // Arrange
        String token = "invalidToken";
        Long userId = 1L;

        when(sessionRepository.findByTokenAndUser_Id(token, userId)).thenReturn(Optional.empty());

        // Act
        SessionStatus status = authService.validate(token, userId);

        // Assert
        assertNull(status);
    }
}