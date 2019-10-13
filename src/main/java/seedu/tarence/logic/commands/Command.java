package seedu.tarence.logic.commands;

import java.util.List;

import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Returns true if command requires prior user input, else false.
     */
    public abstract boolean needsInput();

    /**
     * Returns true if prior command is required for execution, else false.
     * Only needed for commands that require prior user input.
     */
    public abstract boolean needsCommand(Command command);



    public void suggestSimilarCommands(Tutorial tutorial, List<ModCode> modCodes) {

    }
}
