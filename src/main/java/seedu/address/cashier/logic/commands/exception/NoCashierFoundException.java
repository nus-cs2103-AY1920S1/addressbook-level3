package seedu.address.cashier.logic.commands.exception;

/**
 * Represents a "no cashier found" error encountered by a parser.
 */
public class NoCashierFoundException extends Exception {

    private String msg;

    public NoCashierFoundException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String toString() {
        return this.msg;
    }
}
