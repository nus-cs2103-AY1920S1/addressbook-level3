package dream.fcard.model.exceptions;

/**
 * Represents an error when deck object is not found.
 */
public class DeckNotFoundException extends Throwable {
    public DeckNotFoundException(Exception cause) {
        super(cause);
    }
}
