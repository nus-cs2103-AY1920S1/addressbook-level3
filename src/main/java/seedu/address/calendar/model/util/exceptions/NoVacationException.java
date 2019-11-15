package seedu.address.calendar.model.util.exceptions;

/**
 * Represents an error when there are no vacations (i.e. school break or holiday).
 */
public class NoVacationException extends Exception {
    public NoVacationException() {
        super("There is no vacation");
    }
}

