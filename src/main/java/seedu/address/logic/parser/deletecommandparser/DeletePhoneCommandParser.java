package seedu.address.logic.parser.deletecommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.deletecommand.DeletePhoneCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeletePhoneCommand object
 */
public class DeletePhoneCommandParser implements Parser<DeletePhoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePhoneCommand
     * and returns a DeletePhoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePhoneCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeletePhoneCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePhoneCommand.MESSAGE_USAGE), pe);
        }
    }

}
