package seedu.address.reimbursement.model.exception;

/**
 * Returns an exception when an invalid deadline is provided.
 */
public class InvalidDeadlineException extends Exception {
    private static String msg = "Format of deadline date is wrong.";

    public InvalidDeadlineException() {
        super(msg);
    }

    public String toString() {
        return this.msg;
    }
}
