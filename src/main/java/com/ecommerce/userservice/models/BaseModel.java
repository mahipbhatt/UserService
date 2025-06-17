package com.ecommerce.userservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Base model for all entities, providing a common ID field.
 *
 * @author mahip.bhatt
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseModel {

    /**
     * The unique identifier for the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Default constructor for JPA.
     */
    public BaseModel() {
    }

    /**
     * Constructor with ID for specific use cases.
     *
     * @param id The unique identifier for the entity.
     */
    public BaseModel(Long id) {
        this.id = id;
    }
}