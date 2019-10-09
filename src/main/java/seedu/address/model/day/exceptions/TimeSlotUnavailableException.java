package seedu.address.model.day.exceptions;

/**
 * Signals that the operation is unable to continue because there is an overlap in time slot.
 */
public class TimeSlotUnavailableException extends RuntimeException {
    public TimeSlotUnavailableException() {
        super("Operation would result in overlap in time slot.");
    }
}
