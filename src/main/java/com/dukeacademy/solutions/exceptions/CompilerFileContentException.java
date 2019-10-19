package com.dukeacademy.solutions.exceptions;

import com.dukeacademy.solutions.models.CompileError;

/**
 * Exception thrown by the compiler if a compilation error occurs due to the contents of the file.
 */
public class CompilerFileContentException extends Exception {
    public CompilerFileContentException(String message) {
        super(message);
    }

    public CompileError getCompileError() {
        return new CompileError(this.getMessage());
    }
}
