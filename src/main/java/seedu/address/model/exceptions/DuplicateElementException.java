package seedu.address.model.exceptions;

/**
 * Signals that the operation will result in duplicate elements.
 */
public class DuplicateElementException extends RuntimeException {
    public <T> DuplicateElementException(T t) {
        super("Operation would result in duplicate elements of " + t.getClass().getName());
    }
}
