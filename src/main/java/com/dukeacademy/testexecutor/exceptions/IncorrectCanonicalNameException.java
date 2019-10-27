package com.dukeacademy.testexecutor.exceptions;

/**
 * Exception that is thrown when the contents of a JavaFile instance does not match the specified
 * canonical name.
 */
public class IncorrectCanonicalNameException extends Exception {
    /**
     * Instantiates a new Incorrect canonical name exception.
     *
     * @param message the message
     */
    public IncorrectCanonicalNameException(String message) {
        super(message);
    }
}
