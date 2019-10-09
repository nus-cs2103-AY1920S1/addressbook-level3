package seedu.address.model.day.exceptions;

/**
 * Signals that the operation is unable to find an activity at the time-slot.
 */
public class TimeSlotNotOccupiedException extends RuntimeException {
    public TimeSlotNotOccupiedException() {
        super("Operation would result in overlap in time-slot.");
    }
}
