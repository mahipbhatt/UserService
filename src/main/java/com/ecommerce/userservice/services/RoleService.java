package com.ecommerce.userservice.services;

import com.ecommerce.userservice.models.Role;
import com.ecommerce.userservice.repositories.RoleRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for managing roles in the application.
 * 
 * @author mahip.bhatt
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    /**
     * Constructor for RoleService.
     *
     * @param roleRepository the repository for role data
     */
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Creates a new role with the specified name and saves it to the repository.
     *
     * @param name the name of the role to be created
     * @return the created and saved {@link Role} object
     */
    public Role createRole(String name) {
        Role role = new Role();
        role.setRole(name);
        return roleRepository.save(role);
    }
}