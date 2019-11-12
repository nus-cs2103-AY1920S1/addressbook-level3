package seedu.ezwatchlist.api.exceptions;

/**
 * Represents an error when application fails to connect online to the API
 */
public class OnlineConnectionException extends Exception {
    private String message;

    public OnlineConnectionException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

