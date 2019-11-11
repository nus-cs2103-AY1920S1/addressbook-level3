package seedu.jarvis.model.cca.exceptions;

/**
 * Signals that cca progress cannot be decreased any further.
 */
public class CcaProgressAtMinException extends RuntimeException {
    public CcaProgressAtMinException() {
        super("Cca progress is already at the lowest level!");
    }
}
