package dream.fcard.model.exceptions;

/**
 * Represents an error when index of choice supplied by user is invalid.
 */
public class ChoiceNotFoundException extends Throwable {
    public ChoiceNotFoundException(Exception cause) {
        super(cause);
    }
}
