package seedu.ezwatchlist.model.show.exceptions;

/**
 * Signals that the operation is unable to find the specified show.
 */
public class ShowNotFoundException extends RuntimeException {
    public ShowNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public ShowNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
