package seedu.address.logic.parser.copycommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.copycommand.CopyCustomerCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CopyCustomerCommand object
 */
public class CopyCustomerCommandParser implements Parser<CopyCustomerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CopyCustomerCommand
     * and returns a CopyCustomerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CopyCustomerCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CopyCustomerCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyCustomerCommand.MESSAGE_USAGE), pe);
        }
    }

}
