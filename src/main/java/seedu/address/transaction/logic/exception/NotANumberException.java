package seedu.address.transaction.logic.exception;

/**
 * Signals that the a number user input was expected but not given.
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
