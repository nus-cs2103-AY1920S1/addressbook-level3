package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewDriverTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewDriverTaskCommand object
 */
public class ViewDriverTaskCommandParser implements Parser<ViewDriverTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewDriverTaskCommand
     * and returns a ViewDriverCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewDriverTaskCommand parse(String args) throws ParseException {

        try {
            int driverId = ParserUtil.parseId(args);
            return new ViewDriverTaskCommand((driverId));
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewDriverTaskCommand.MESSAGE_USAGE), pe);
        }
    }
}
