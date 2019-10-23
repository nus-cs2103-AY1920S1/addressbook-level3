package seedu.address.model.visit.exceptions;

/**
 * Signals that the operation could not find a visit (e.g. index out of bounds)
 */
public class VisitNotFoundException extends RuntimeException {
    public VisitNotFoundException() {
        super("Visit could not be found.");
    }
}
