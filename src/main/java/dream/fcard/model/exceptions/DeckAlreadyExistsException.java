package dream.fcard.model.exceptions;

/**
 * Represents an error when deck object is not found.
 */
public class DeckAlreadyExistsException extends Throwable {
    public DeckAlreadyExistsException(String cause) {
        super(cause);
    }
}
