package seedu.jarvis.model.finance.exceptions;

/**
 * Signals that the operation will result in duplicate Installments.
 */
public class DuplicateInstallmentException extends RuntimeException {
    public DuplicateInstallmentException() {
        super("Operation would result in duplicate installments");
    }
}
