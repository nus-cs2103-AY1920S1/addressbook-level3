package seedu.ichifund.logic.parser.repeater;

import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.ichifund.commons.core.index.Index;
import seedu.ichifund.logic.commands.repeater.DeleteRepeaterCommand;
import seedu.ichifund.logic.parser.Parser;
import seedu.ichifund.logic.parser.ParserUtil;
import seedu.ichifund.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteRepeaterCommand object
 */
public class DeleteRepeaterCommandParser implements Parser<DeleteRepeaterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteRepeaterCommand
     * and returns a DeleteRepeaterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteRepeaterCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteRepeaterCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRepeaterCommand.MESSAGE_USAGE), pe);
        }
    }

}
