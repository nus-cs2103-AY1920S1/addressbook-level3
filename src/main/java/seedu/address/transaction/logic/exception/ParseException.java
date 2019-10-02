package seedu.address.transaction.logic.exception;

public class ParseException extends Exception {
    private String msg;

    public ParseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String toString() {
        return this.msg;
    }
}
