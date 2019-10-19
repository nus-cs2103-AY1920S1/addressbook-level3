package com.dukeacademy.testexecutor.exceptions;

public class ServiceInitializationException extends Exception {
    public ServiceInitializationException(String message) {
        super(message);
    }

    public ServiceInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
