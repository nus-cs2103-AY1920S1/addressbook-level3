package seedu.address.overview.logic.commands;

import java.util.logging.Logger;

import seedu.address.overview.logic.commands.exception.InvalidValueException;
import seedu.address.overview.model.Model;
import seedu.address.person.commons.core.LogsCenter;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    protected final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws InvalidValueException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws InvalidValueException;
}
