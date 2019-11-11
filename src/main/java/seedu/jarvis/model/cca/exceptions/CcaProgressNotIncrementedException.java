package seedu.jarvis.model.cca.exceptions;

/**
 * Signals that the cca progress is not incremented yet.
 */
public class CcaProgressNotIncrementedException extends Throwable {
    public CcaProgressNotIncrementedException() {
        super("Cca progress is not yet incremented!");
    }
}
