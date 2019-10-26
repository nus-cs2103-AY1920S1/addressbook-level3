package com.dukeacademy.testexecutor.environment.exceptions;

public class ClearEnvironmentException  extends Exception {
    public ClearEnvironmentException(String message) {
        super(message);
    }

    public ClearEnvironmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
