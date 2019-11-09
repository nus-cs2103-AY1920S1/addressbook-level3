package seedu.address.model.password.exceptions;

/**
 * Signals that there are errors during dictionary construction.
 */
public class DictionaryNotFoundException extends RuntimeException {

    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public DictionaryNotFoundException(String message) {
        super(message);
    }

    /**
     * @param message should contain relevant information on the failed constraint(s)
     * @param cause of the main exception
     */
    public DictionaryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
