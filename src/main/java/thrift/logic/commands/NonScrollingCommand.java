package thrift.logic.commands;

import thrift.logic.commands.exceptions.CommandException;
import thrift.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed; does not make display scroll to
 * modified item.
 */
public abstract class NonScrollingCommand extends Command {

    /**
     * Executes the command that needs no display scrolling and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;
}
