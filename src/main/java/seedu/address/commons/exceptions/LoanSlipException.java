package seedu.address.commons.exceptions;

/**
 * Exception thrown when an error is encountered while generating a loan slip.
 */
public class LoanSlipException extends Exception {

    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public LoanSlipException(String message) {
        super(message);
    }
}
