package dream.fcard.model.exceptions;

/**
 * Represents an error when deck object is not found.
 */
public class NoDeckHistoryException extends Throwable {
    public NoDeckHistoryException(String cause) {
        super(cause);
    }
}
