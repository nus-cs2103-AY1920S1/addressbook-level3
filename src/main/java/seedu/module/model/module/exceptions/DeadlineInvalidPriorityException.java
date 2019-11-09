package seedu.module.model.module.exceptions;

/**
 * Signals that the priority is invalid.
 */
public class DeadlineInvalidPriorityException extends RuntimeException {
    public DeadlineInvalidPriorityException(String message) {
        super(message);
    }
}
