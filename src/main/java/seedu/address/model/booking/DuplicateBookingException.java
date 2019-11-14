package seedu.address.model.booking;

/**
 * Exception thrown when attempting an operation that results in duplicate trips.
 */
public class DuplicateBookingException extends RuntimeException {
    public DuplicateBookingException() {
        super("Operation would result in duplicate bookings");
    }
}
