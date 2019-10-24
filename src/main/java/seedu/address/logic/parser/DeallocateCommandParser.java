package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeallocateCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeallocateCommand object
 */
public class DeallocateCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the DeallocateCommand
     * and returns a DeallocateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeallocateCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeallocateCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeallocateCommand.MESSAGE_USAGE), pe);
        }
    }
}
