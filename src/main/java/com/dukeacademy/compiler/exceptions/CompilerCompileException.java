package com.dukeacademy.compiler.exceptions;

public class CompilerCompileException extends Exception {
    public CompilerCompileException(String message) {
        super(message);
    }

    public CompilerCompileException(String message, Throwable cause) {
        super(message, cause);
    }
}
