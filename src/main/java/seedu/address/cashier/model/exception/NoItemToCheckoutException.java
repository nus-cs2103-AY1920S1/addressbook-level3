package seedu.address.cashier.model.exception;

/**
 * Signals that there is no item in the sales list.
 */
public class NoItemToCheckoutException extends Exception {

    private String msg;

    public NoItemToCheckoutException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String toString() {
        return this.msg;
    }
}
