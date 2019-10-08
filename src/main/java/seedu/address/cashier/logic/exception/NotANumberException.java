package seedu.address.cashier.logic.exception;

public class NotANumberException extends Exception {

    private String msg;

    public NotANumberException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String toString() {
        return this.msg;
    }
}

