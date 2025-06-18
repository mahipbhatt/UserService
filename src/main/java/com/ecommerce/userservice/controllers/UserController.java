package com.ecommerce.userservice.controllers;

import com.ecommerce.userservice.dtos.SetUserRolesRequestDto;
import com.ecommerce.userservice.dtos.UserDto;
import com.ecommerce.userservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing user-related operations.
 * Provides endpoints for retrieving user details and setting user roles.
 *
 * @author mahip.bhatt
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * Constructor for UserController.
     * Initializes the UserService dependency.
     *
     * @param userService The service for user-related operations.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to retrieve user details by user ID.
     *
     * @param userId The ID of the user whose details are to be retrieved.
     * @return ResponseEntity containing the user details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") Long userId) {
        UserDto userDto = userService.getUserDetails(userId);
        return ResponseEntity.ok(userDto);
    }

    /**
     * Endpoint to set roles for a user.
     *
     * @param userId  The ID of the user whose roles are to be updated.
     * @param request The request containing the list of role IDs to be assigned to the user.
     * @return ResponseEntity containing the updated user details.
     */
    @PostMapping("/{id}/roles")
    public ResponseEntity<UserDto> setUserRoles(@PathVariable("id") Long userId, @RequestBody SetUserRolesRequestDto request) {
        UserDto userDto = userService.setUserRoles(userId, request.getRoleIds());
        return ResponseEntity.ok(userDto);
    }
}