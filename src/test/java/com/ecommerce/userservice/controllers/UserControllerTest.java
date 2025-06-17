package com.ecommerce.userservice.controllers;

import com.ecommerce.userservice.dtos.SetUserRolesRequestDto;
import com.ecommerce.userservice.dtos.UserDto;
import com.ecommerce.userservice.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserService userService;
    private UserController userController;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    void testGetUserDetailsSuccess() {
        // Arrange
        Long userId = 1L;
        UserDto expectedUser = new UserDto("test@example.com", Collections.emptySet());
        when(userService.getUserDetails(userId)).thenReturn(expectedUser);

        // Act
        ResponseEntity<UserDto> response = userController.getUserDetails(userId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedUser, response.getBody());
        verify(userService, times(1)).getUserDetails(userId);
    }

    @Test
    void testGetUserDetailsFailure() {
        // Arrange
        Long userId = 1L;
        when(userService.getUserDetails(userId)).thenThrow(new RuntimeException("User not found"));

        // Act & Assert
        try {
            userController.getUserDetails(userId);
        } catch (RuntimeException e) {
            assertEquals("User not found", e.getMessage());
        }
        verify(userService, times(1)).getUserDetails(userId);
    }

    @Test
    void testSetUserRolesSuccess() {
        // Arrange
        Long userId = 1L;
        SetUserRolesRequestDto request = new SetUserRolesRequestDto(List.of(1L, 2L));
        UserDto expectedUser = new UserDto("test@example.com", Collections.emptySet());
        when(userService.setUserRoles(userId, request.getRoleIds())).thenReturn(expectedUser);

        // Act
        ResponseEntity<UserDto> response = userController.setUserRoles(userId, request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedUser, response.getBody());
        verify(userService, times(1)).setUserRoles(userId, request.getRoleIds());
    }

    @Test
    void testSetUserRolesFailure() {
        // Arrange
        Long userId = 1L;
        SetUserRolesRequestDto request = new SetUserRolesRequestDto(List.of(1L, 2L));
        when(userService.setUserRoles(userId, request.getRoleIds())).thenThrow(new RuntimeException("Role assignment failed"));

        // Act & Assert
        try {
            userController.setUserRoles(userId, request);
        } catch (RuntimeException e) {
            assertEquals("Role assignment failed", e.getMessage());
        }
        verify(userService, times(1)).setUserRoles(userId, request.getRoleIds());
    }

    @Test
    void testSetUserRolesWithEmptyRoleIds() {
        // Arrange
        Long userId = 1L;
        SetUserRolesRequestDto request = new SetUserRolesRequestDto(Collections.emptyList());
        when(userService.setUserRoles(userId, request.getRoleIds())).thenThrow(new IllegalArgumentException("Role IDs cannot be empty"));

        // Act & Assert
        try {
            userController.setUserRoles(userId, request);
        } catch (IllegalArgumentException e) {
            assertEquals("Role IDs cannot be empty", e.getMessage());
        }
        verify(userService, times(1)).setUserRoles(userId, request.getRoleIds());
    }
}