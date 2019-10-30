package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SortSpendingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortSpendingCommand object
 */
public class SortSpendingParser implements Parser<SortSpendingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortTaskCommand
     * and returns a SortTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortSpendingCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SortSpendingCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortSpendingCommand.MESSAGE_USAGE), pe);
        }
    }
}
