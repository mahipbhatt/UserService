package com.ecommerce.userservice.services;

import com.ecommerce.userservice.dtos.UserDto;
import com.ecommerce.userservice.models.Role;
import com.ecommerce.userservice.models.User;
import com.ecommerce.userservice.repositories.RoleRepository;
import com.ecommerce.userservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service class for managing user-related operations.
 *
 * @author mahip.bhatt
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    /**
     * Constructor for UserService.
     *
     * @param userRepository the repository for user data
     * @param roleRepository the repository for role data
     */
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Retrieves user details for the given user ID.
     *
     * @param userId the ID of the user
     * @return a {@link UserDto} containing user details, or null if the user is not found
     */
    public UserDto getUserDetails(Long userId) {
        return userRepository.findById(userId)
                .map(UserDto::from)
                .orElse(null);
    }

    /**
     * Assigns roles to a user based on the provided role IDs.
     *
     * @param userId  the ID of the user
     * @param roleIds the list of role IDs to assign
     * @return a {@link UserDto} containing updated user details, or null if the user is not found
     */
    public UserDto setUserRoles(Long userId, List<Long> roleIds) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return null;
        }

        List<Role> roles = roleRepository.findAllByIdIn(roleIds);
        User user = userOptional.get();
        user.setRoles(Set.copyOf(roles));

        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }
}