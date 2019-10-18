package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteEarningsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteEarningsCommand object
 */
public class DeleteEarningsCommandParser implements Parser<DeleteEarningsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteEarningsCommand
     * and returns a DeleteEarningsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteEarningsCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteEarningsCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEarningsCommand.MESSAGE_USAGE), pe);
        }
    }

}
