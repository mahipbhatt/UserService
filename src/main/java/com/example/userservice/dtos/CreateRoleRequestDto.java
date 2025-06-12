package com.example.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for creating a new role.
 * Contains the necessary information to create a role.
 * 
 * @author mahip.bhatt
 */
@Getter
@Setter
public class CreateRoleRequestDto {

    /**
     * The name of the role to be created.
     */
    private String name;
}