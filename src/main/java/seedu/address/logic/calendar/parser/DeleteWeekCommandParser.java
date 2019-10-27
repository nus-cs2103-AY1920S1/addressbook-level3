package seedu.address.logic.calendar.parser;

import seedu.address.logic.calendar.commands.DeleteWeekCommand;
import seedu.address.logic.calendar.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteWeekCommand object
 */
public class DeleteWeekCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteWeekCommand parse(String args) throws ParseException {
        return new DeleteWeekCommand();
    }
}
