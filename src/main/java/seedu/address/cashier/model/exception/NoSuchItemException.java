package seedu.address.cashier.model.exception;

/**
 * Signals that the person the user input is not in the data base.
 */
public class NoSuchItemException extends Exception {

    private String msg;

    public NoSuchItemException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String toString() {
        return this.msg;
    }

}
