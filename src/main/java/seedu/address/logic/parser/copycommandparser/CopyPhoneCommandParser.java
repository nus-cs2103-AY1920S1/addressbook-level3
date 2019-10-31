package seedu.address.logic.parser.copycommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.copycommand.CopyPhoneCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CopyPhoneCommand object
 */
public class CopyPhoneCommandParser implements Parser<CopyPhoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CopyPhoneCommand
     * and returns a CopyPhoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CopyPhoneCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CopyPhoneCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyPhoneCommand.MESSAGE_USAGE), pe);
        }
    }

}
