package seedu.address.model.note.exceptions;

/**
 * Signals that the operation is unable to find the specified person.
 */
public class InvalidRedoException extends NullPointerException {
    public InvalidRedoException() {
        super("Cannot Redo anymore!");
    }
}
