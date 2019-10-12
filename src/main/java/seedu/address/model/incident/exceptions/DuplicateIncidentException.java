package seedu.address.model.incident.exceptions;

/**
 * Signals that the operation will result in duplicate Incidents
 * (Incidents are considered duplicates if they have the same identity).
 */
public class DuplicateIncidentException extends RuntimeException {
    public DuplicateIncidentException() {
        super("Operation would result in duplicate incidents");
    }
}
