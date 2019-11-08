package seedu.address.calendar.model.util.exceptions;

public class NoVacationException extends RuntimeException {
    public NoVacationException() {
        super("There is no vacation");
    }
}

