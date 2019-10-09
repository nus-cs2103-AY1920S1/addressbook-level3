package seedu.address.model.day.exceptions;

/**
 * Signals that the operation is unable to continue because there is an overlap in time-slot.
 */
public class TimeSlotOccupiedException extends RuntimeException {
    public TimeSlotOccupiedException() {
        super("Operation would result in overlap in time-slot.");
    }
}
