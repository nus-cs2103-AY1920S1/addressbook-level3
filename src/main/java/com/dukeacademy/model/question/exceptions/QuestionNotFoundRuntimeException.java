package com.dukeacademy.model.question.exceptions;

/**
 * Unchecked exception thrown when a question is not found.
 */
public class QuestionNotFoundRuntimeException extends RuntimeException {
    public QuestionNotFoundRuntimeException() {
    }

    public QuestionNotFoundRuntimeException(String message) {
        super(message);
    }
}
