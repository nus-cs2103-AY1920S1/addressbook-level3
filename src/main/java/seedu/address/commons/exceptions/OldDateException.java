package seedu.address.commons.exceptions;

/**
 * Represents an error where the date is too old, and hence not accepted by the app.
 */
public class OldDateException extends Exception {

    public OldDateException(String message) {
        super(message);
    }
}
