package com.dukeacademy.logic.commands.exceptions;

/**
 * Exception thrown when the arguments provided for a command is invalid.
 */
public class InvalidCommandArgumentsException extends Exception {
    public InvalidCommandArgumentsException(String message) {
        super(message);
    }
}
