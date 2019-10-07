package seedu.address.transaction.model.exception;

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
