package seedu.ifridge.model.food.exceptions;

/**
 * Signals that the operation is unable to find the specified person.
 */
public class ShoppingItemNotFoundException extends RuntimeException {
    public ShoppingItemNotFoundException() {
        super("Shopping item is not found.");
    }
}
