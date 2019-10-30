package seedu.tarence.model.tutorial.exceptions;

/**
 * Signals that the operation will result in duplicate Event.
 */
public class DuplicateAssignmentException extends RuntimeException {
    public DuplicateAssignmentException() {
        super("Operation would result in duplicate Assignments");
    }
}
