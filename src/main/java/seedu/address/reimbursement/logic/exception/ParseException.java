package seedu.address.reimbursement.logic.exception;

/**
 * Represents an exception occurred when parsing.
 */
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
