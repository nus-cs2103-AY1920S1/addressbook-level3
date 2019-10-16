package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CompleteOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CompleteOrderCommand object
 */
public class CompleteOrderCommandParser implements Parser<CompleteOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CompleteOrderCommand
     * and returns a CompleteOrderCommand object for execution.
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
