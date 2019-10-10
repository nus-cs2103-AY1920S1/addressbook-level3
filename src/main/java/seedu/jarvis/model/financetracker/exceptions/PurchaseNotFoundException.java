package seedu.jarvis.model.financetracker.exceptions;

/**
 * Signals that the operation will try to access a purchase that does not exist.
 */
public class PurchaseNotFoundException extends RuntimeException {
    public PurchaseNotFoundException() {
        super("Operation is trying to access a non-existent purchase");
    }
}
