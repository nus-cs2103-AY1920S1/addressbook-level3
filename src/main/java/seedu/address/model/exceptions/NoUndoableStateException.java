package seedu.address.model.exceptions;

/**
 * Represents an error which occurs during execution of a {@link UndoCommand}.
 */
public class NoUndoableStateException extends Exception {

    public static final String MESSAGE_UNDO_FAILURE = "There is no action to undo!";

    public NoUndoableStateException() {
        super(MESSAGE_UNDO_FAILURE);
    }

}
