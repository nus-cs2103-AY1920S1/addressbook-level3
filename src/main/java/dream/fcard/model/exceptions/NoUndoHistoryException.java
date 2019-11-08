package dream.fcard.model.exceptions;

/**
 * Represents an error when deck object is not found.
 */
public class NoUndoHistoryException extends Throwable {
    public NoUndoHistoryException(String cause) {
        super(cause);
    }
}
