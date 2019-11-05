package seedu.guilttrip.logic.parser.conditioncommandparsers;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.commands.conditioncommands.DeleteConditionCommand;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.exceptions.ParseException;

/**
 * Parses input argument and creates a new DeleteConditionCommand.
 */
public class DeleteConditionCommandParser implements Parser<DeleteConditionCommand> {
    @Override
    public DeleteConditionCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteConditionCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteConditionCommand.MESSAGE_USAGE), pe);
        }
    }
}
