package com.ecommerce.userservice.controllers;

import com.ecommerce.userservice.dtos.CreateRoleRequestDto;
import com.ecommerce.userservice.models.Role;
import com.ecommerce.userservice.services.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RoleControllerTest {

    private RoleService roleService;
    private RoleController roleController;

    @BeforeEach
    void setUp() {
        roleService = mock(RoleService.class);
        roleController = new RoleController(roleService);
    }

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