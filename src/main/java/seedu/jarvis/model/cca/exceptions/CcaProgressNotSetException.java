package seedu.jarvis.model.cca.exceptions;

/**
 * Signals that the Cca progress is not yet set.
 */
public class CcaProgressNotSetException extends RuntimeException {
    public CcaProgressNotSetException() {
        super("Cca progress is not yet set!");
    }
}
