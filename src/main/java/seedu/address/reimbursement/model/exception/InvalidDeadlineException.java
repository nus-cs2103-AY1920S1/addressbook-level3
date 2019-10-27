package seedu.address.reimbursement.model.exception;

/**
 * Returns an exception when an invalid deadline is provided.
 */
public class InvalidDeadlineException extends Exception {
    private static final String MSG = "Format of deadline date is wrong." + System.lineSeparator()
            + "The format should be dd-mmm-yyyy (eg" + ".21-Sep-2019)";

    public InvalidDeadlineException() {
        super(MSG);
    }

    public String toString() {
        return this.MSG;
    }
}
