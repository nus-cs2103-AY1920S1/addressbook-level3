package seedu.address.transaction.logic.exception;

/**
 * Signals that the user input is incorrectly given in general.
 */
public class ParseException extends Exception {
    private String msg;

    public ParseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String toString() {
        return this.msg;
    }
}
