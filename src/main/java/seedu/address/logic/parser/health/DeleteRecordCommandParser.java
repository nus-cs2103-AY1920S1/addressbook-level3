package seedu.address.logic.parser.health;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.health.DeleteRecordCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteRecordCommand object
 */
public class DeleteRecordCommandParser implements Parser<DeleteRecordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteRecordCommand
     * and returns a DeleteRecordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteRecordCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteRecordCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRecordCommand.MESSAGE_USAGE), pe);
        }
    }

}
