package seedu.address.cashier.logic.exception;

public class InsufficientAmountException extends Exception {

    private String msg;

    public InsufficientAmountException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String toString() {
        return this.msg;
    }
}

