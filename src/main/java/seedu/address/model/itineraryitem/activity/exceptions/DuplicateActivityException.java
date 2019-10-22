package seedu.address.model.itineraryitem.activity.exceptions;

/**
 * Signals that the operation will result in duplicate Activities (Activities are considered duplicates if they
 * have the same identity).
 */
public class DuplicateActivityException extends RuntimeException {
    public DuplicateActivityException() {
        super("Operation would result in duplicate activities");
    }
}
