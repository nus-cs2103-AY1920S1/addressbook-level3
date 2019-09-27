package seedu.address.logic.commands.common;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a undoable command with hidden internal logic and the ability to be executed.
 */
public abstract class ReversibleCommand extends Command {

    /**
     * Undoes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult undo(Model model) throws CommandException;
}
