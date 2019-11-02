package seedu.planner.logic.events.exceptions;

import seedu.planner.commons.exceptions.IllegalValueException;

/**
 * Represents an error which occurs during creation of a {@link Event}.
 */
public class EventException extends IllegalValueException {
    public EventException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public EventException(String message, Throwable cause) {
        super(message, cause);
    }
}
