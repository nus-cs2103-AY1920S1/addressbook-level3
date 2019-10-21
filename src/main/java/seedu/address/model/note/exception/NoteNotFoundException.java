package seedu.address.model.note.exception;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException() {
        super("No note with that title exists!");
    }
}
