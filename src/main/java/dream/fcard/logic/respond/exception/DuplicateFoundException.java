package dream.fcard.logic.respond.exception;

/**
 * Exception thrown when duplicates detected
 */
public class DuplicateFoundException extends Throwable {
    /**
     *
     *
     * @param cause
     */
    public DuplicateFoundException(String cause) {
        super(cause);
    }
}
