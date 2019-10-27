package com.dukeacademy.testexecutor.compiler.exceptions;

import com.dukeacademy.testexecutor.models.CompileError;

/**
 * Exception thrown by the compiler if a compilation error occurs due to the contents of the file.
 */
public class CompilerFileContentException extends Exception {
    /**
     * Instantiates a new Compiler file content exception.
     *
     * @param message the message
     */
    public CompilerFileContentException(String message) {
        super(message);
    }

    /**
     * Gets compile error.
     *
     * @return the compile error
     */
    public CompileError getCompileError() {
        return new CompileError(this.getMessage());
    }
}
