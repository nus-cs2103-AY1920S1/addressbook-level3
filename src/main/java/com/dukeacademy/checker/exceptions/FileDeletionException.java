package com.dukeacademy.checker.exceptions;

public class FileDeletionException extends Exception {
    public FileDeletionException(String message) {
        super(message);
    }

    public FileDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
