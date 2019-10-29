package seedu.address.model.currency.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateCurrencyException extends RuntimeException {
    public DuplicateCurrencyException() {
        super("Operation would result in duplicate persons");
    }
}
