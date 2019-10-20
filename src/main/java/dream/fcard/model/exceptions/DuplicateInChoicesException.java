package dream.fcard.model.exceptions;

/**
 * Represents an error when there is a duplicate in choices.
 */
public class DuplicateInChoicesException extends Throwable {
    public DuplicateInChoicesException(String cause) {
        super(cause);
    }
}
