package seedu.address.model.exceptions;

/**
 * Represents an error which occurs during execution of a {@link RedoCommand}.
 */
public class NoRedoableStateException extends Exception {

    public static final String MESSAGE_REDO_FAILURE = "There is no action to redo!";

    public NoRedoableStateException() {
        super(MESSAGE_REDO_FAILURE);
    }

}
