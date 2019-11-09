package seedu.module.model.module.exceptions;

/**
 * Signals that the deadline task is already marked as undone.
 */
public class DeadlineMarkException extends RuntimeException {
    public DeadlineMarkException(String message) {
        super(message);
    }
}
