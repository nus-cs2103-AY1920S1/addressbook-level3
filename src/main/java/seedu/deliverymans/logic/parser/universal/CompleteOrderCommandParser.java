package seedu.deliverymans.logic.parser.universal;

import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.commands.universal.CompleteOrderCommand;
import seedu.deliverymans.logic.parser.Parser;
import seedu.deliverymans.logic.parser.ParserUtil;
import seedu.deliverymans.logic.parser.exceptions.ParseException;

/**
 * Tofill.
 */
public class CompleteOrderCommandParser implements Parser<CompleteOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteOrderCommand
     * and returns a DeleteOrderCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CompleteOrderCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CompleteOrderCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteOrderCommand.MESSAGE_USAGE), pe);
        }
    }
}
