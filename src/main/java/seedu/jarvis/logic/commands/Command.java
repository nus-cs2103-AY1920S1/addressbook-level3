package seedu.jarvis.logic.commands;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Gets the command word of the command.
     *
     * @return {@code String} representation of the command word.
     */
    public abstract String getCommandWord();

    /**
     * Returns whether the command has an inverse execution.
     * If the command has no inverse execution, then calling {@code executeInverse}
     * will be guaranteed to always throw a {@code CommandException}.
     *
     * @return Whether the command has an inverse execution.
     */
    public abstract boolean hasInverseExecution();

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return Feedback message of the operation result for display.
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Executes the inverse of the command and returns the result message.
     *
     * @param model {@code Model} which the command should inversely operate on.
     * @return Feedback message of the inverse operation result for display.
     * @throws CommandException If an error occurs during the command inverse execution.
     */
    public abstract CommandResult executeInverse(Model model) throws CommandException;

    /**
     * Gets a {@code JsonAdaptedCommand} from a {@code Command} for local storage purposes.
     *
     * @return {@code JsonAdaptedCommand}.
     * @throws InvalidCommandToJsonException If command should not be adapted to JSON format.
     */
    public abstract JsonAdaptedCommand adaptToJsonAdaptedCommand() throws InvalidCommandToJsonException;
}
