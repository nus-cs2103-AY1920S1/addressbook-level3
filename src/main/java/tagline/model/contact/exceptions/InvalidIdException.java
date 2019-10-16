package tagline.model.contact.exceptions;

/**
 * Signals that the operation will make an invalid Id.
 */
public class InvalidIdException extends RuntimeException {
    public InvalidIdException() { }

    public InvalidIdException(String message) {
        super(message);
    }
}
