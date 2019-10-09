package com.dukeacademy.checker.exceptions;

public class FileDirectoryDeletionException extends Exception {
    public FileDirectoryDeletionException(String message) {
        super(message);
    }

    public FileDirectoryDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
