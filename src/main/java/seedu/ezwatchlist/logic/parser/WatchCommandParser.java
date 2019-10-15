package seedu.ezwatchlist.logic.parser;

import seedu.ezwatchlist.commons.core.index.Index;
import seedu.ezwatchlist.logic.commands.DeleteCommand;
import seedu.ezwatchlist.logic.commands.WatchCommand;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;
import seedu.ezwatchlist.commons.core.Messages;

/**
 * Parses input arguments and creates a new WatchCommand object
 */
public class WatchCommandParser implements Parser<WatchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the WatchCommand
     * and returns a WatchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public WatchCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new WatchCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, WatchCommand.MESSAGE_USAGE), pe);
        }
    }

}
