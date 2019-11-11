package seedu.address.inventory.logic.parser.exception;

/**
 * Signals that items in inventory cannot be modified as cashier mode is on.
 */
public class OnCashierModeException extends Exception {
    private String msg;

    public OnCashierModeException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String toString() {
        return this.msg;
    }
}
