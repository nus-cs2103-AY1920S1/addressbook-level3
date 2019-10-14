package seedu.address.inventory.logic.exception;

/**
 * Signals that the user input is not a number.
 */
public class NotANumberException extends Exception {

    private String msg;

    public NotANumberException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String toString() {
        return this.msg;
    }
}
