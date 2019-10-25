package seedu.ifridge.model.food.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateShoppingItemException extends RuntimeException {
    public DuplicateShoppingItemException() {
        super("Operation would result in duplicate food items");
    }
}
