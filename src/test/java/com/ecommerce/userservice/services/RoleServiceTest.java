package com.ecommerce.userservice.services;

import com.ecommerce.userservice.models.Role;
import com.ecommerce.userservice.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link RoleService}.
 * This class tests the functionality of the RoleService methods using mocked dependencies.
 *
 * @author mahip.bhatt
 */
class RoleServiceTest {

    private RoleRepository roleRepository;
    private RoleService roleService;

    /**
     * Sets up the test environment by initializing mocked dependencies.
     */
    @BeforeEach
    void setUp() {
        roleRepository = mock(RoleRepository.class);
        roleService = new RoleService(roleRepository);
    }

    /**
     * Tests the createRole functionality for a successful scenario.
     */
    @Test
    void testCreateRoleSuccess() {
        // Arrange
        String roleName = "ADMIN";
        Role expectedRole = new Role();
        expectedRole.setRole(roleName);

        when(roleRepository.save(any(Role.class))).thenReturn(expectedRole);

        // Act
        Role createdRole = roleService.createRole(roleName);

        // Assert
        assertNotNull(createdRole);
        assertEquals(roleName, createdRole.getRole());
        verify(roleRepository, times(1)).save(any(Role.class));
    }
}