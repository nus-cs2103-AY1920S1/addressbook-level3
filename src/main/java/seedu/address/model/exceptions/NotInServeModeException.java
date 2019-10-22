package seedu.address.model.exceptions;

/**
 * Signals that the Serve mode operation that is supposed to be carried out cannot be carried out
 * as the model is not in Serve mode.
 */
public class NotInServeModeException extends RuntimeException {
    public NotInServeModeException() {
        super("Not in Serve mode!");
    }
}
