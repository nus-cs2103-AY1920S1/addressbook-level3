package seedu.jarvis.model.cca.exceptions;

/**
 * Suggests that cca progress cannot be decreased any further.
 */
public class CcaProgressAtZeroException extends RuntimeException{
    public CcaProgressAtZeroException() {
        super("Cca progress is already at the lowest level!");
    }
}
