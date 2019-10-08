package seedu.address.transaction.logic.exception;

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
