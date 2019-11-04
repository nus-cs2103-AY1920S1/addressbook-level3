package seedu.ezwatchlist.api.exceptions;

/**
 * Represents an error when application fails to connect online to the API
 */
public class OnlineConnectionException extends Exception {

    public OnlineConnectionException(String message) {
        super(message);
    }
}

