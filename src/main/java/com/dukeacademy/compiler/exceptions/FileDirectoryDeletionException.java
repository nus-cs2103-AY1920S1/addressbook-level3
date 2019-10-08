package com.dukeacademy.compiler.exceptions;

public class FileDirectoryDeletionException extends Exception {
    public FileDirectoryDeletionException(String message) {
        super(message);
    }

    public FileDirectoryDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
