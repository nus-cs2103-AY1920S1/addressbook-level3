package com.dukeacademy.checker.exceptions;

public class FileDirectoryCreationException extends Exception {
    public FileDirectoryCreationException(String message) {
        super(message);
    }

    public FileDirectoryCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
