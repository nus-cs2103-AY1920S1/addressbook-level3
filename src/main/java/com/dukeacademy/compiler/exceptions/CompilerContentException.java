package com.dukeacademy.compiler.exceptions;

public class CompilerContentException extends Exception {
    public CompilerContentException(String message) {
        super(message);
    }

    public CompilerContentException(String message, Throwable cause) {
        super(message, cause);
    }
}
