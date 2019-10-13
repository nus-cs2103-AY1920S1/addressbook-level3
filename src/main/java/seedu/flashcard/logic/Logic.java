package seedu.flashcard.logic;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.flashcard.logic.commands.CommandResult;
import seedu.flashcard.logic.parser.exceptions.ParseException;

/**
 * API of the logic component
 */
public interface Logic {

    /**
     * Executes the command and returns the result.
     * @param CommandText The command as entered by the user
     * @return the result of the command execution
     * @throws CommandException if an error occurs during command execution
     * @throws ParseException if an error occurs during parsing
     */
    CommandResult execute(String CommandText) throws CommandException, ParseException;
}
