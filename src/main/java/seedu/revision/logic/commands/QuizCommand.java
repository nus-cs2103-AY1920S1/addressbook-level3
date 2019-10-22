package seedu.revision.logic.commands;

import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.Model;
import seedu.revision.model.answerable.Answerable;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class QuizCommand {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(
            Model model, Answerable currentAnswerable) throws CommandException, ParseException;

}
