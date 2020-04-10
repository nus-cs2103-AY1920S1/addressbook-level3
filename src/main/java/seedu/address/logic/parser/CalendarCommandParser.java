package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.CalendarCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.date.AthletickDate;

/**
 * Parses input arguments and creates a new CalendarCommand object
 */
public class CalendarCommandParser implements Parser<CalendarCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CalendarCommand
     * and returns a CalendarCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public CalendarCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.length() == 6 || trimmedArgs.length() == 8) {
            AthletickDate date = ParserUtil.parseDate(trimmedArgs);
            return new CalendarCommand(date);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarCommand.MESSAGE_USAGE));
        }
    }
}
