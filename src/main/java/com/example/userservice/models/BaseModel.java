package com.example.userservice.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Base model class for entities.
 * Provides a common ID field with auto-generation strategy.
 * All entity classes can extend this class to inherit the ID field.
 * 
 * @author mahip.bhatt
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseModel {

    /**
     * The unique identifier for the entity.
     * Automatically generated using the IDENTITY strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}