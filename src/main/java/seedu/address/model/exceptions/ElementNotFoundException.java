package seedu.address.model.exceptions;

/**
 * Signals that the operation is unable to find the specified element.
 */
public class ElementNotFoundException extends RuntimeException {
    public <T> ElementNotFoundException(T t) {
        super("Element " + t + " was not found.");
    }
}
