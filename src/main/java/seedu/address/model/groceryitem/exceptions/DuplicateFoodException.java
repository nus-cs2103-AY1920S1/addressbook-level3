package seedu.address.model.groceryitem.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateFoodException extends RuntimeException {
    public DuplicateFoodException() {
        super("Operation would result in duplicate food items");
    }
}
