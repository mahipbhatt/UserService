package com.example.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the User Service application.
 * This class bootstraps the Spring Boot application.
 *
 * @author mahip.bhatt
 */
@SpringBootApplication
public class UserServiceApplication {

    /**
     * Main method to start the User Service application.
     *
     * @param args Command-line arguments passed during application startup.
     */
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}