package seedu.address.commons.exceptions;

/**
 * Signals that there are erorrs during dictionary construction.
 */
public class DictionaryException extends Exception {

    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public DictionaryException(String message) {
        super(message);
    }
    /**
     * @param message should contain relevant information on the failed constraint(s)
     * @param cause of the main exception
     */
    public DictionaryException(String message, Throwable cause) {
        super(message, cause);
    }
}
