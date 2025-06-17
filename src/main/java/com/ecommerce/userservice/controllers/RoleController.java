package com.ecommerce.userservice.controllers;

import com.ecommerce.userservice.dtos.CreateRoleRequestDto;
import com.ecommerce.userservice.models.Role;
import com.ecommerce.userservice.services.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing roles in the application.
 * Provides an endpoint for creating new roles.
 * 
 * @author mahip.bhatt
 */
@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    /**
     * Constructor for RoleController.
     * Initializes the RoleService dependency.
     *
     * @param roleService The service for role-related operations.
     */
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Endpoint for creating a new role.
     *
     * @param request The request containing the name of the role to be created.
     * @return ResponseEntity containing the created Role object and HTTP status.
     */
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody CreateRoleRequestDto request) {
        Role role = roleService.createRole(request.getName());
        return ResponseEntity.ok(role);
    }
}
