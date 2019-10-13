package seedu.address.model.notif.exceptions;

/**
 * Signals that the operation will result in duplicate Notifs(Notifs are considered duplicates if they have the same
 * identity).
 */
public class DuplicateNotifException extends RuntimeException {
    public DuplicateNotifException() {
        super("Operation would result in duplicate notifs");
    }
}

