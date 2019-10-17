package dream.fcard.model.exceptions;

/**
 * Represents an error when index is not found.
 */
public class IndexNotFoundException extends Throwable {
    public IndexNotFoundException(Exception cause) {
        super(cause);
    }
}
