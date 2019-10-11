package seedu.address.model.visittask.exceptions;

/**
 * Signals that the operation will result in duplicate Visit Tasks
 * (Visit Tasks are considered duplicates if they have the same identity).
 */
public class DuplicateVisitTaskException extends RuntimeException {
    public DuplicateVisitTaskException() {
        super("Operation would result in duplicate visit tasks");
    }
}
