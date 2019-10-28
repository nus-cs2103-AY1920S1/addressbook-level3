package com.dukeacademy.logic.program.exceptions;

/**
 * Exception thrown when the ProgramSubmissionLogicManager instance has been closed but an operation was requested.
 */
public class SubmissionLogicManagerClosedException extends RuntimeException {
    /**
     * Instantiates a new Submission logic manager closed exception.
     */
    public SubmissionLogicManagerClosedException() {
        super("The ProgramSubmissionLogicManager instance has already been closed.");
    }
}
