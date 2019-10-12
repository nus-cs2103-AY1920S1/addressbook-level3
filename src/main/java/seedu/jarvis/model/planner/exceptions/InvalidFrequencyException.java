package seedu.jarvis.model.planner.exceptions;

/**
 * Signals that a user has input an invalid value for the frequency of a task.
 */
public class InvalidFrequencyException extends RuntimeException {
    public InvalidFrequencyException() {
        super("Frequency given is invalid. Valid Frequency values are: daily, weekly, monthly, yearly.");
    }
}
