package seedu.address.model.customer.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateCustomerException extends RuntimeException {
    public DuplicateCustomerException() {
        super("Operation would result in duplicate persons");
    }
}
