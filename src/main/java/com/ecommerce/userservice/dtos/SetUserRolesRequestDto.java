package com.ecommerce.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object for setting roles for a user.
 * Contains the list of role IDs to be assigned to the user.
 *
 * @author mahip.bhatt
 */
@Getter
@Setter
@AllArgsConstructor
public class SetUserRolesRequestDto {

    /**
     * The list of role IDs to be assigned to the user.
     */
    private List<Long> roleIds;
}