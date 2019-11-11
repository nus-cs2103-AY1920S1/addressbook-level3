package seedu.address.logic.calendar.parser;

import seedu.address.logic.calendar.commands.ClearWeekCommand;
import seedu.address.logic.calendar.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClearWeekCommand object
 */
public class ClearWeekCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClearWeekCommand parse(String args) throws ParseException {
        return new ClearWeekCommand();
    }
}
