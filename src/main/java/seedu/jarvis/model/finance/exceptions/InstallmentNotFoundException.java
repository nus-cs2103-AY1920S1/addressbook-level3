package seedu.jarvis.model.finance.exceptions;

/**
 * Signals that the operation will try to access an installment that does not exist.
 */
public class InstallmentNotFoundException extends RuntimeException {
    public InstallmentNotFoundException() {
        super("Operation is trying to access a non-existent installment");
    }
}
