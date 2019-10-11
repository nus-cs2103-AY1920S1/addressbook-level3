package seedu.address.logic.commands.exceptions;

import seedu.address.commons.exceptions.AlfredException;

/**
 * Represents an error which occurs during execution of a {@link Command}.
 * There are 4 main scenario where CommandException is thrown:
 * 1) Could not store data to file
 * 2) Person index is invalid
 * 3) Duplicate person with the same identity is added to model
 * 4) Type of entity(mentor, participant, team) is invalid)
 */
public class CommandException extends AlfredException {
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
