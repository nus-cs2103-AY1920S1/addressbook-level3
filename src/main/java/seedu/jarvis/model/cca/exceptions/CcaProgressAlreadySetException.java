package seedu.jarvis.model.cca.exceptions;

/**
 * Signals that the Cca progress is already set.
 */
public class CcaProgressAlreadySetException extends RuntimeException {
    public CcaProgressAlreadySetException() {
        super("Cca progress is already set!");
    }
}
