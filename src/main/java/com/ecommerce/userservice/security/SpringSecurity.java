package com.ecommerce.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuration class for Spring Security.
 * Provides security-related beans for the application.
 *
 * @author mahip.bhatt
 */
@Configuration
public class SpringSecurity {

    /**
     * Bean definition for {@link BCryptPasswordEncoder}.
     * This encoder is used for encoding passwords securely.
     *
     * @return a new instance of {@link BCryptPasswordEncoder}
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}