package seedu.jarvis.model.planner.exceptions;

/**
 * Signals that the user has input an invalid value for the priority of a task.
 */
public class InvalidPriorityException extends RuntimeException {

    public InvalidPriorityException() {
        super("Invalid Priority given. Valid Priority levels are: low, med and high");
    }
}
