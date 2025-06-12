package com.example.userservice.security.service;

import com.example.userservice.models.User;
import com.example.userservice.repositories.UserRepository;
import com.example.userservice.security.models.CustomSpringUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation of {@link UserDetailsService} to load user-specific data for authentication.
 * 
 * @author mahip.bhatt
 */
@Service
public class CustomSpringUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructs a new {@code CustomSpringUserDetailsService} with the specified {@link UserRepository}.
     *
     * @param userRepository the user repository to retrieve user data
     */
    public CustomSpringUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by their email address.
     *
     * @param email the email address of the user
     * @return a {@link UserDetails} object containing user-specific data
     * @throws UsernameNotFoundException if no user is found with the given email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new CustomSpringUserDetails(user);
    }
}