package seedu.address.reimbursement.exceptions;

public class ReimbursementUpdatedException extends RuntimeException{
    public ReimbursementUpdatedException() {
        super("This reimbursement has already been completed.");
    }
}
