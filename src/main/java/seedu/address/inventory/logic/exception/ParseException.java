package seedu.address.inventory.logic.exception;

/**
 * Signals that there was an error in parsing.
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
