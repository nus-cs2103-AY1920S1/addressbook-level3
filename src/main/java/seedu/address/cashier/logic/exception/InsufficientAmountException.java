package seedu.address.cashier.logic.exception;

/**
 * Represents an "insufficient amount" error encountered by a parser.
 */
public class InsufficientAmountException extends Exception {

    private String msg;

    public InsufficientAmountException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String toString() {
        return this.msg;
    }
}

