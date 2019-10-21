package seedu.address.model.task.exceptions;

/**
 * Signals that the operation will result in duplicate tasks.
 */
public class DuplicateTaskException extends RuntimeException {
    public DuplicateTaskException() {
        super("Operation would result in duplicate tasks");
    }
}
