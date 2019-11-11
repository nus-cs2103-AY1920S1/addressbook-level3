package seedu.address.inventory.logic.parser.exception;

/**
 * Signals that the user input is either not a number, too large, or negative.
 */
public class InvalidNumberException extends Exception {
    private String msg;

    public InvalidNumberException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String toString() {
        return this.msg;
    }
}
