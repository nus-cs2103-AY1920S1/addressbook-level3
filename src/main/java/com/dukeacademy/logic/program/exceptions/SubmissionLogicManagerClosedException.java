package com.dukeacademy.logic.program.exceptions;

/**
 * Exception thrown when the ProgramSubmissionLogicManager instance has been closed but an operation was requested.
 */
public class SubmissionLogicManagerClosedException extends RuntimeException {
    public SubmissionLogicManagerClosedException() {
        super("The ProgramSubmissionLogicManager instance has already been closed.");
    }
}
