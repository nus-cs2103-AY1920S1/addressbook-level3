package seedu.address.model.note.exceptions;

/**
 * Signals that the operation will result in duplicate Notes (Notess are considered duplicates if they have the same
 * identity).
 */
public class DuplicateNotesExceptions extends RuntimeException {
    public DuplicateNotesExceptions() {
        super("Operation would result in duplicate notes");
    }
}
