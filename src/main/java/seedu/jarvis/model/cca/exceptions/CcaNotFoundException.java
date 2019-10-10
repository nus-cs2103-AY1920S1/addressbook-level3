package seedu.jarvis.model.cca.exceptions;

/**
 * Signals that the operation is unable to find the specified person.
 */
public class CcaNotFoundException extends RuntimeException {
    public CcaNotFoundException() {
        super("Cca is not found");
    }
}
