package seedu.jarvis.model.address.person.exceptions;

/**
 * Signals that the operation will result in duplicate Purchases.
 */
public class DuplicatePurchaseException extends RuntimeException {

    public DuplicatePurchaseException() {
        super("Operation would result in duplicate installments");
    }
}
