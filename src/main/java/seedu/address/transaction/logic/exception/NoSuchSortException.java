package seedu.address.transaction.logic.exception;

/**
 * Signals that the user input is incorrect for a sort command.
 */
public class NoSuchSortException extends Exception {
    private String msg;

    public NoSuchSortException(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public String toString() {
        return this.msg;
    }
}
