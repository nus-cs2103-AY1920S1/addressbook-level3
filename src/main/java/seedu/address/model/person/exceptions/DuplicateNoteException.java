package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in lecture notes with duplicate titles.
 */
public class DuplicateNoteException extends RuntimeException {
    public DuplicateNoteException() {
        super("Operation would result in duplicate lecture notes");
    }
}
