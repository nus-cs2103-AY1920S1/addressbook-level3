package seedu.address.model.task.exceptions;

/**
 * Represents an exception thrown for redundant operations.
 */
public class RedundantOperationException extends RuntimeException {
    public RedundantOperationException(String message) {
        super(message);
    }
}
