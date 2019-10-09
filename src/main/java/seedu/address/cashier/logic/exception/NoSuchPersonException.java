package seedu.address.cashier.logic.exception;

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

