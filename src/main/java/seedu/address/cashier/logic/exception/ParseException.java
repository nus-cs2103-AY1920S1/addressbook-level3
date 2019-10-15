package seedu.address.cashier.logic.exception;

/**
 * Represents a parse error encountered by a parser.
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

