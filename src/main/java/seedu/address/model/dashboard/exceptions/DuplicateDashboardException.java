package seedu.address.model.dashboard.exceptions;

/**
 * Signals that the operation will result in duplicate Dashboards
 * (Dashboards are considered duplicates if they have the same
 * identity).
 */
public class DuplicateDashboardException extends RuntimeException {
    public DuplicateDashboardException() {
        super("Operation would result in duplicate dashboards");
    }
}
