package seedu.weme.logic.parser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.MemeLikeCommand;
import seedu.weme.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MemeLikeCommand object
 */
public class MemeLikeCommandParser implements Parser<MemeLikeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MemeLikeCommand
     * and returns a MemeLikeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MemeLikeCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MemeLikeCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemeLikeCommand.MESSAGE_USAGE), pe);
        }
    }

}
