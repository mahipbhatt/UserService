package com.ecommerce.userservice.repositories;

import com.ecommerce.userservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing {@link Role} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and custom queries.
 *
 * @author mahip.bhatt
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds all roles with the specified IDs.
     *
     * @param roleIds the list of role IDs to search for
     * @return a list of {@link Role} entities matching the given IDs
     */
    List<Role> findAllByIdIn(List<Long> roleIds);
}