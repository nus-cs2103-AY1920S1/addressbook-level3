package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewDriverWindowCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewDriverWindowCommand object
 */
public class ViewDriverWindowCommandParser implements Parser<ViewDriverWindowCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ViewDriverWindowCommand
     * and returns an ViewDriverWindowCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewDriverWindowCommand parse(String args) throws ParseException {
        try {
            int id = ParserUtil.parseId(args);
            return new ViewDriverWindowCommand(id);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewDriverWindowCommand.MESSAGE_USAGE), pe);
        }
    }
}
