package seedu.weme.model.template.exceptions;

/**
 * Signal that a meme creation has failed.
 */
public class MemeCreationException extends Exception {
    public MemeCreationException(Exception cause) {
        super("Unable to create meme", cause);
    }
}
