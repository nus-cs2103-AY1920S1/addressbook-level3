package seedu.address.reimbursement.exceptions;

/**
 * Signals that the operation is unable to find the specified reimbursement.
 */
public class ReimbursementNotFoundException extends RuntimeException {
    public ReimbursementNotFoundException() {
        super("Operation is unable to find the reimbursement");
    }
}
