package seedu.address.transaction.logic.parser;

import seedu.address.transaction.logic.commands.Command;
import seedu.address.transaction.logic.commands.exception.NoSuchSortException;
import seedu.address.transaction.logic.parser.exception.ParseException;

/**
 * Parses input arguments and creates a new Command object
 */
public interface IndependentCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of commands
     * and returns a Command object for execution.
     * @throws ParseException If the user input does not conform the expected format
     * @throws NoSuchSortException If the user inputs a wrong sort command
     */
    Command parse(String args) throws ParseException, NoSuchSortException;
}
