package seedu.address.reimbursement.model.exception;

/**
 * Returns an exception if there are no reimbursement records for a particular person.
 */
public class NoSuchPersonReimbursementException extends Exception {
    private static String msg = "The person is not in the reimbursement list.";

    public NoSuchPersonReimbursementException() {
        super(msg);
    }

    public String toString() {
        return this.msg;
    }
}
