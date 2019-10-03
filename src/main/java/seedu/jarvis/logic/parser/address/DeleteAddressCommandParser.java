package seedu.jarvis.logic.parser.address;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.address.DeleteAddressCommand;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteAddressCommand object
 */
public class DeleteAddressCommandParser implements Parser<DeleteAddressCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAddressCommand
     * and returns a DeleteAddressCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAddressCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteAddressCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAddressCommand.MESSAGE_USAGE), pe);
        }
    }

}
