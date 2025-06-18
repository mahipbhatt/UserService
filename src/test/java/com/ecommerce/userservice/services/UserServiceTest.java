package com.ecommerce.userservice.services;

import com.ecommerce.userservice.dtos.UserDto;
import com.ecommerce.userservice.models.Role;
import com.ecommerce.userservice.models.User;
import com.ecommerce.userservice.repositories.RoleRepository;
import com.ecommerce.userservice.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link UserService}.
 * This class tests the functionality of the UserService methods using mocked dependencies.
 *
 * @author mahip.bhatt
 */
class UserServiceTest {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserService userService;

    /**
     * Sets up the test environment by initializing mocked dependencies.
     */
    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        userService = new UserService(userRepository, roleRepository);
    }

    /**
     * Tests the getUserDetails functionality for a successful scenario.
     */
    @Test
    void testGetUserDetailsSuccess() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setEmail("test@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        UserDto userDto = userService.getUserDetails(userId);

        // Assert
        assertNotNull(userDto);
        assertEquals("test@example.com", userDto.getEmail());
        verify(userRepository, times(1)).findById(userId);
    }

    /**
     * Tests the getUserDetails functionality when the user is not found.
     */
    @Test
    void testGetUserDetailsUserNotFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        UserDto userDto = userService.getUserDetails(userId);

        // Assert
        assertNull(userDto);
        verify(userRepository, times(1)).findById(userId);
    }

    /**
     * Tests the setUserRoles functionality for a successful scenario.
     */
    @Test
    void testSetUserRolesSuccess() {
        // Arrange
        Long userId = 1L;
        List<Long> roleIds = List.of(1L, 2L);
        User user = new User();
        user.setId(userId);
        Role role1 = new Role();
        role1.setId(1L);
        role1.setRole("ADMIN");
        Role role2 = new Role();
        role2.setId(2L);
        role2.setRole("USER");
        List<Role> roles = List.of(role1, role2);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleRepository.findAllByIdIn(roleIds)).thenReturn(roles);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserDto userDto = userService.setUserRoles(userId, roleIds);

        // Assert
        assertNotNull(userDto);
        assertEquals(2, userDto.getRoles().size());
        verify(userRepository, times(1)).findById(userId);
        verify(roleRepository, times(1)).findAllByIdIn(roleIds);
        verify(userRepository, times(1)).save(user);
    }

    /**
     * Tests the setUserRoles functionality when the user is not found.
     */
    @Test
    void testSetUserRolesUserNotFound() {
        // Arrange
        Long userId = 1L;
        List<Long> roleIds = List.of(1L, 2L);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        UserDto userDto = userService.setUserRoles(userId, roleIds);

        // Assert
        assertNull(userDto);
        verify(userRepository, times(1)).findById(userId);
        verify(roleRepository, never()).findAllByIdIn(roleIds);
        verify(userRepository, never()).save(any(User.class));
    }
}