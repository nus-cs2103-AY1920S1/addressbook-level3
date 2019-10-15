package seedu.address.transaction.model.exception;

/**
 * Signals that the user inputs a person not in the data base.
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
