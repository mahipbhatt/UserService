package com.ecommerce.userservice.controllers;

import com.ecommerce.userservice.dtos.CreateRoleRequestDto;
import com.ecommerce.userservice.models.Role;
import com.ecommerce.userservice.services.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link RoleController}.
 * This class tests the functionality of the RoleController methods using mocked dependencies.
 *
 * @author mahip.bhatt
 */
class RoleControllerTest {

    private RoleService roleService;
    private RoleController roleController;

    /**
     * Sets up the test environment by initializing mocked dependencies.
     */
    @BeforeEach
    void setUp() {
        roleService = mock(RoleService.class);
        roleController = new RoleController(roleService);
    }

    /**
     * Tests the createRole functionality for a successful scenario.
     */
    @Test
    void testCreateRoleSuccess() {
        // Arrange
        CreateRoleRequestDto request = new CreateRoleRequestDto("ADMIN");
        Role expectedRole = new Role();
        expectedRole.setRole("ADMIN");

        when(roleService.createRole("ADMIN")).thenReturn(expectedRole);

        // Act
        ResponseEntity<Role> response = roleController.createRole(request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedRole, response.getBody());
        verify(roleService, times(1)).createRole("ADMIN");
    }

    /**
     * Tests the createRole functionality for a failure scenario.
     */
    @Test
    void testCreateRoleFailure() {
        // Arrange
        CreateRoleRequestDto request = new CreateRoleRequestDto("INVALID_ROLE");
        when(roleService.createRole("INVALID_ROLE")).thenThrow(new RuntimeException("Role creation failed"));

        // Act & Assert
        try {
            roleController.createRole(request);
        } catch (RuntimeException e) {
            assertEquals("Role creation failed", e.getMessage());
        }
        verify(roleService, times(1)).createRole("INVALID_ROLE");
    }

    /**
     * Tests the createRole functionality when the role name is empty.
     */
    @Test
    void testCreateRoleWithEmptyName() {
        // Arrange
        CreateRoleRequestDto request = new CreateRoleRequestDto("");
        when(roleService.createRole("")).thenThrow(new IllegalArgumentException("Role name cannot be empty"));

        // Act & Assert
        try {
            roleController.createRole(request);
        } catch (IllegalArgumentException e) {
            assertEquals("Role name cannot be empty", e.getMessage());
        }
    }
}