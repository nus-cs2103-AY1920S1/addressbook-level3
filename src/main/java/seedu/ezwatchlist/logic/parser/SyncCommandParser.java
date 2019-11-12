package seedu.ezwatchlist.logic.parser;

import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;
import seedu.ezwatchlist.commons.core.index.Index;
import seedu.ezwatchlist.commons.core.messages.Messages;
import seedu.ezwatchlist.logic.commands.SyncCommand;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SyncCommand object
 */
public class SyncCommandParser implements Parser<SyncCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an SyncCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SyncCommand parse(String args, String currentPanel) throws ParseException, OnlineConnectionException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SyncCommand(index);
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SyncCommand.MESSAGE_USAGE), e);
        }
    }

}
