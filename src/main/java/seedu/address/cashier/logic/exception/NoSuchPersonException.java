package seedu.address.cashier.logic.exception;

/**
 * Represents a "no such person" error encountered by a parser.
 */
public class NoSuchPersonException extends Exception {

    private String msg;

    public NoSuchPersonException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String toString() {
        return this.msg;
    }
}

