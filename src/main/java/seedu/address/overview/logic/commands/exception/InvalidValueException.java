package seedu.address.overview.logic.commands.exception;

/**
 * Signals that the amount specified is an invalid value.
 */
public class InvalidValueException extends Exception {
    private String msg;

    public InvalidValueException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String toString() {
        return this.msg;
    }
}
