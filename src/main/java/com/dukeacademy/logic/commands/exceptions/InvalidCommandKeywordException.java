package com.dukeacademy.logic.commands.exceptions;

/**
 * Exception thrown by Command package when the user's command is not recognized.
 */
public class InvalidCommandKeywordException extends Exception {
    /**
     * Instantiates a new Invalid command keyword exception.
     *
     * @param message the message
     */
    public InvalidCommandKeywordException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Invalid command keyword exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public InvalidCommandKeywordException(String message, Throwable cause) {
        super(message, cause);
    }
}
