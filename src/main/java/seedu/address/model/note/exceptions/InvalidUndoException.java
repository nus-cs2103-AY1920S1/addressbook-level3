package seedu.address.model.note.exceptions;

/**
 * Signals that the operation is unable to find the specified person.
 */
public class InvalidUndoException extends NullPointerException {
    public InvalidUndoException() {
        super("Cannot undo anymore!");
    }
}
