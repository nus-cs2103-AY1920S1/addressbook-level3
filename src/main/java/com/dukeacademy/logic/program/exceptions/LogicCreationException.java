package com.dukeacademy.logic.program.exceptions;

/**
 * Exception thrown when a logic instance fails to be created.
 */
public class LogicCreationException extends Exception {
    /**
     * Instantiates a new Logic creation exception.
     *
     * @param message the message
     */
    public LogicCreationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Logic creation exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public LogicCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
