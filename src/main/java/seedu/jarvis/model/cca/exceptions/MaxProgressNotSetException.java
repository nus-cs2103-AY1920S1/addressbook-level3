package seedu.jarvis.model.cca.exceptions;

/**
 * Signals that the max progress is not yet set.
 */
public class MaxProgressNotSetException extends RuntimeException {
    public MaxProgressNotSetException() {
        super("Max progress is not yet set!");
    }
}
