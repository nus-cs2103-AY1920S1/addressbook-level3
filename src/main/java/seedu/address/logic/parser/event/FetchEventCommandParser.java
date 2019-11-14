package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.FetchEventCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new FetchEventCommand object
 */
public class FetchEventCommandParser implements Parser<FetchEventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FetchEventCommand
     * and returns a FetchEventCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FetchEventCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new FetchEventCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FetchEventCommand.MESSAGE_USAGE), pe);
        }
    }
}
