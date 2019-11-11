package mams.logic.commands;

import mams.logic.commands.exceptions.CommandException;
import mams.logic.history.FilterOnlyCommandHistory;
import mams.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @param commandHistory
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model, FilterOnlyCommandHistory commandHistory) throws CommandException;

}
