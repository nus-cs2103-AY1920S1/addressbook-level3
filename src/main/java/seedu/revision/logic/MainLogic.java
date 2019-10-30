package seedu.revision.logic;

import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.Answerable;

/**
 * Inherits Logic interface. Added functionality to original Logic interface.
 */
public interface MainLogic extends Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    CommandResult execute(String commandText, Answerable currentAnswerable) throws CommandException, ParseException;
}
