package dream.fcard.model.exceptions;

/**
 * Represents an error when there is an invalid input is not found.
 */
public class InvalidInputException extends Throwable {
    public InvalidInputException(String cause) {
        super(cause);
    }
}
