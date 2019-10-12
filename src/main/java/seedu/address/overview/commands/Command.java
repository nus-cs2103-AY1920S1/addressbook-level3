package seedu.address.overview.commands;

import seedu.address.overview.model.Model;
import seedu.address.overview.model.exception.NoSuchIndexException;
import seedu.address.overview.model.exception.NoSuchPersonException;
import seedu.address.person.logic.commands.exceptions.CommandException;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws NoSuchIndexException If an error occurs when that Transaction is not in the list.
     * @throws CommandException If an error occurs during command execution.
     * @throws NoSuchPersonException If an error occurs when a Person is not in the data base.
     */
    public abstract CommandResult execute(Model model) throws CommandException;
}
