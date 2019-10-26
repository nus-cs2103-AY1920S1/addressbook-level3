package com.dukeacademy.testexecutor.exceptions;

/**
 * Exception that is thrown when the contents of a JavaFile instance does not match the specified
 * canonical name.
 */
public class IncorrectCanonicalNameException extends Exception {
    public IncorrectCanonicalNameException(String message) {
        super(message);
    }
}
