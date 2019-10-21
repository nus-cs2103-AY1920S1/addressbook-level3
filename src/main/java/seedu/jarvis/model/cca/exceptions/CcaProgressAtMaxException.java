package seedu.jarvis.model.cca.exceptions;

/**
 * Signals that Cca progress is already at its maximum.
 */
public class CcaProgressAtMaxException extends RuntimeException {
    public CcaProgressAtMaxException() {
        super("Cca progress already at maximum!");
    }
}
