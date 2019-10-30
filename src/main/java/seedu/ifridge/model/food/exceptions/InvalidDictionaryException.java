package seedu.ifridge.model.food.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class InvalidDictionaryException extends RuntimeException {
    public InvalidDictionaryException() {
        super("Dictionary is not updated with all items");
    }
}
