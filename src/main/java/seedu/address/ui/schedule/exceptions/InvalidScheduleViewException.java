package seedu.address.ui.schedule.exceptions;

/**
 * Exception thrown when UI receives an invalid schedule.
 */
public class InvalidScheduleViewException extends Exception {
    public InvalidScheduleViewException(String msg) {
        super(msg);
    }
}
