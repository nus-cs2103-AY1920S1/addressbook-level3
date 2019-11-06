package seedu.deliverymans.logic.parser.universal;

import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.commands.universal.AssignOrderCommand;
import seedu.deliverymans.logic.parser.Parser;
import seedu.deliverymans.logic.parser.ParserUtil;
import seedu.deliverymans.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AssignOrderCommand object
 */
public class AssignOrderCommandParser implements Parser<AssignOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignOrderCommand
     * and returns a AssignOrderCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignOrderCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new AssignOrderCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignOrderCommand.MESSAGE_USAGE), pe);
        }
    }
}
