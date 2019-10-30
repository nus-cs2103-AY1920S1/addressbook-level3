package seedu.deliverymans.logic.parser.deliveryman;

import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.commands.deliveryman.EnterRecordCommand;
import seedu.deliverymans.logic.parser.ParserUtil;
import seedu.deliverymans.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EnterRecordCommand object
 */
public class EnterRecordCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the EnterRecordCommand
     * and returns a EnterRecordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EnterRecordCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new EnterRecordCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EnterRecordCommand.MESSAGE_USAGE), pe);
        }
    }
}
