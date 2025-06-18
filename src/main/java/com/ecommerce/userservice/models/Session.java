package com.ecommerce.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Entity representing a user session in the application.
 * Extends {@link BaseModel} to inherit the ID field.
 *
 * @author mahip.bhatt
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Session extends BaseModel {

    /**
     * The token associated with the session.
     */
    private String token;

    /**
     * The expiration date and time of the session.
     */
    private Date expiringAt;

    /**
     * The user associated with the session.
     * Represents a many-to-one relationship with the {@link User} entity.
     */
    @ManyToOne
    private User user;

    /**
     * The status of the session.
     * Stored as an ordinal value in the database.
     */
    @Enumerated(EnumType.ORDINAL)
    private SessionStatus sessionStatus;
}