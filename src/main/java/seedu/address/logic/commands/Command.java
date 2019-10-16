package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Validates the command parameters.
     * @throws CommandException If parameters are invalid.
     */
    protected abstract void validate(Model model) throws CommandException;

    /**
     * Executes the command, without validation, and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    protected abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Executes the command with validation and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public CommandResult run(Model model) throws CommandException {
        validate(model);
        return execute(model);
    }
}
