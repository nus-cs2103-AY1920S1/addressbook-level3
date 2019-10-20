package seedu.address.model.note.exceptions;

/**
 * Signals that the operation will result in duplicate Notes (Notes are considered duplicates if they have the same
 * note title).
 */
public class DuplicateNoteException extends RuntimeException {
    public DuplicateNoteException() {
        super("Operation would result in duplicate notes");
    }
}
