package seedu.address.reimbursement.model.exception;

/**
 * Returns an exception when an invalid deadline is provided.
 */
public class InvalidDeadlineException extends Exception {
    private static String msg = "Format of deadline date is wrong.\nThe format should be dd-mmm-yyyy (eg.21-Sep-2019)";

    public InvalidDeadlineException() {
        super(msg);
    }

    public String toString() {
        return this.msg;
    }
}
