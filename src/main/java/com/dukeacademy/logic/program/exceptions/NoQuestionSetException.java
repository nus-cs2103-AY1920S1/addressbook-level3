package com.dukeacademy.logic.program.exceptions;

/**
 * Exception thrown by ProgramSubmissionLogic when a user program is submitted before a question is set.
 */
public class NoQuestionSetException extends RuntimeException {
    public NoQuestionSetException() {
        super("There is no question set for the ProgramSubmissionLogicManager instance.");
    }
}
