package com.ecommerce.userservice.security.services;

import com.ecommerce.userservice.models.User;
import com.ecommerce.userservice.repositories.UserRepository;
import com.ecommerce.userservice.security.service.CustomSpringUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link CustomSpringUserDetailsService}.
 * This class tests the functionality of the CustomSpringUserDetailsService methods using mocked dependencies.
 *
 * @author mahip.bhatt
 */
class CustomSpringUserDetailsServiceTest {

    private UserRepository userRepository;
    private CustomSpringUserDetailsService userDetailsService;

    /**
     * Sets up the test environment by initializing mocked dependencies.
     */
    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userDetailsService = new CustomSpringUserDetailsService(userRepository);
    }

    /**
     * Tests the loadUserByUsername functionality for a successful scenario.
     */
    @Test
    void testLoadUserByUsernameSuccess() {
        // Arrange
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        user.setPassword("password");
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // Assert
        assertEquals(email, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        verify(userRepository, times(1)).findByEmail(email);
    }

    /**
     * Tests the loadUserByUsername functionality for a failure scenario.
     */
    @Test
    void testLoadUserByUsernameFailure() {
        // Arrange
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(email));
        verify(userRepository, times(1)).findByEmail(email);
    }
}