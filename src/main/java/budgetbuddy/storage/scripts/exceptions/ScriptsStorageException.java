package budgetbuddy.storage.scripts.exceptions;

/**
 * Represents an issue that occurs while reading or writing the script library.
 */
public class ScriptsStorageException extends Exception {
    /**
     * Constructs a {@link ScriptsStorageException} with the given message.
     *
     * @param message the message
     */
    public ScriptsStorageException(String message) {
        super(message);
    }

    /**
     * Constructs a {@link ScriptsStorageException} with the given message and cause.
     *
     * @param message the message
     * @param cause the cause
     */
    public ScriptsStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
