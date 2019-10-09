package com.dukeacademy.solution.exceptions;

public class ProgramExecutorException extends Exception {
    public ProgramExecutorException(String message) {
        super(message);
    }

    public ProgramExecutorException(String message, Throwable cause) {
        super(message, cause);
    }
}
