package seedu.address.model.incident.exceptions;

/**
 * Signals that the operation is unable to find the specified incident.
 */
public class IncidentNotFoundException extends RuntimeException {
    public IncidentNotFoundException() {
        super("Incident is not found.");
    }
}
