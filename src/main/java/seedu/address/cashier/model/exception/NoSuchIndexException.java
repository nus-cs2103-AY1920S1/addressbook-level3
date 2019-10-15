package seedu.address.cashier.model.exception;

/**
 * Signals that the index that does not exist.
 */
public class NoSuchIndexException extends Exception {

    private String msg;

    public NoSuchIndexException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String toString() {
        return this.msg;
    }

}
