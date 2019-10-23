package com.dukeacademy.logic.program.exceptions;

/**
 * Exception thrown when the ProgramSubmissionLogic instance does not have a submission channel set and attempts
 * to submit a program through the submission channel.
 */
public class SubmissionChannelNotSetException extends RuntimeException {
}
