package com.ecommerce.userservice.dtos;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class CreateRoleRequestDto {

    /**
     * The name of the role to be created.
     */
    private String name;
}