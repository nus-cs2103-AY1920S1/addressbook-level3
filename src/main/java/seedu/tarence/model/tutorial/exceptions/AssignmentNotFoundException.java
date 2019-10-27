package seedu.tarence.model.tutorial.exceptions;

/**
 * Signals that the operation is unable to find the specified Assignment.
 */
public class AssignmentNotFoundException extends RuntimeException {
    public AssignmentNotFoundException() {
        super("Operation is unable to find the specified Assignment.");
    }
}
