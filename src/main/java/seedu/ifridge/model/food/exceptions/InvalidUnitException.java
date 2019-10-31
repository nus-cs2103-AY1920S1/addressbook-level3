package seedu.ifridge.model.food.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class InvalidUnitException extends RuntimeException {
    public InvalidUnitException() {
        super("Operation would result in food items with conflicting units");
    }

    public InvalidUnitException(String message) {
        super(message);
    }
}
