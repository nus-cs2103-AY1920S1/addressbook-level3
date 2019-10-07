package seedu.address.transaction.model.exception;

public class NoSuchPersonException extends Exception {

    private String msg;

    public NoSuchPersonException(String msg) {
        super(msg);
    }

    //public NoSuchPersonException() {}

    public String toString() {
        return this.msg;
    }
}
