package seedu.planner.logic.commands.exceptions;

import seedu.planner.commons.exceptions.IllegalValueException;

/**
 * Represents an error which occurs during execution of a {@link Command}.
 */
public class CommandException extends IllegalValueException {
    public CommandException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
