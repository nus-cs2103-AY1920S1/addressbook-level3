package seedu.address.model.accommodation.exceptions;

/**
 * Signals that the operation will result in duplicate Activities (Activities are considered duplicates if they
 * have the same identity).
 */
public class DuplicateAccommodationException extends RuntimeException {
    public DuplicateAccommodationException() {
        super("Operation would result in duplicate accommodations");
    }
}
