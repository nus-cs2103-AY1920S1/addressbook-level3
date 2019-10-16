package seedu.address.model.deadline.exceptions;

//@@author dalsontws
/**
 * Signals that the operation will result in duplicate Deadline
 * (Deadline are considered duplicates if they have the same
 * identity).
 */
public class DuplicateDeadlineException extends RuntimeException {
    public DuplicateDeadlineException() {
        super("Operation would result in duplicate deadlines");
    }
}
