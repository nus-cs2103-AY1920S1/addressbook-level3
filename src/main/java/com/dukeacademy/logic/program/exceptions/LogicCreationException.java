package com.dukeacademy.logic.program.exceptions;

/**
 * Exception thrown when a logic instance fails to be created.
 */
public class LogicCreationException extends Exception {
    public LogicCreationException(String message) {
        super(message);
    }

    public LogicCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
