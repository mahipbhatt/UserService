package com.example.userservice.models;

/**
 * Enum representing the status of a user session.
 * Used to indicate whether a session is currently active or has ended.
 *
 * @author mahip.bhatt
 */
public enum SessionStatus {

    /**
     * Indicates that the session is currently active.
     */
    ACTIVE,

    /**
     * Indicates that the session has ended.
     */
    ENDED,
}