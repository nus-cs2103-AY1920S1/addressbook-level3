package seedu.address.cashier.logic.exception;

/**
 * Represents an "negative quantity" error encountered by a parser.
 */
public class NegativeQuantityException extends Exception {

    private String msg;

    public NegativeQuantityException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String toString() {
        return this.msg;
    }
}
