package com.dukeacademy.logic.commands.exceptions;

/**
 * Exception thrown by Command package when the user's command is not recognized.
 */
public class InvalidCommandKeywordException extends Exception {
    public InvalidCommandKeywordException(String message) {
        super(message);
    }

    public InvalidCommandKeywordException(String message, Throwable cause) {
        super(message, cause);
    }
}
