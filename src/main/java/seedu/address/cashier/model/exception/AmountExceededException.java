package seedu.address.cashier.model.exception;

/**
 * Signals that the total amount has exceeded $999999.99.
 */
public class AmountExceededException extends Exception {

    private String msg;

    public AmountExceededException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String toString() {
        return this.msg;
    }
}
