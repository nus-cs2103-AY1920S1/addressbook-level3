package seedu.algobase.ui;

import seedu.algobase.ui.action.UiAction;

/**
 * Represents an error which occurs during execution of a {@link UiAction}.
 */
public class UiActionException extends Exception {
    public UiActionException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code UiActionException} with the specified detail {@code message} and {@code cause}.
     */
    public UiActionException(String message, Throwable cause) {
        super(message, cause);
    }
}
