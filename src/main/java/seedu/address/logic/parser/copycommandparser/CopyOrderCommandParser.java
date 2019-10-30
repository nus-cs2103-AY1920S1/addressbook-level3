package seedu.address.logic.parser.copycommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.copycommand.CopyOrderCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CopyOrderCommand object
 */
public class CopyOrderCommandParser implements Parser<CopyOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CopyOrderCommand
     * and returns a CopyOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CopyOrderCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CopyOrderCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyOrderCommand.MESSAGE_USAGE), pe);
        }
    }

}
