package seedu.address.reimbursement.model.exception;

/**
 * Returns an exception if there are no reimbursement records for a particular person.
 */
public class NoSuchPersonReimbursementException extends Exception {
    private static final String MSG = "The person is not in the reimbursement list.";

    public NoSuchPersonReimbursementException() {
        super(MSG);
    }

    public String toString() {
        return this.MSG;
    }
}
