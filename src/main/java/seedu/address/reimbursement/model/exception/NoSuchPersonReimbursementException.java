package seedu.address.reimbursement.model.exception;

public class NoSuchPersonReimbursementException extends Exception {
    private static String msg = "The person is not in the reimbursement list.";

    public NoSuchPersonReimbursementException() {
        super(msg);
    }

    public String toString() {
        return this.msg;
    }
}
