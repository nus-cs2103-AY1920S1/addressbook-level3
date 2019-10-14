package seedu.address.inventory.commands;

import seedu.address.inventory.model.Model;
import seedu.address.inventory.model.exception.NoSuchIndexException;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws NoSuchIndexException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model)
            throws Exception;
}
