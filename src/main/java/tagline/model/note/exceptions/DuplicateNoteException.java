package tagline.model.note.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateNoteException extends RuntimeException {
    public DuplicateNoteException() {
        super("Operation would result in duplicate notes");
    }
}
