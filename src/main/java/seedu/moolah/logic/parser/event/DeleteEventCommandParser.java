package seedu.moolah.logic.parser.event;

import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.moolah.commons.core.index.Index;
import seedu.moolah.logic.commands.event.DeleteEventCommand;
import seedu.moolah.logic.parser.Parser;
import seedu.moolah.logic.parser.ParserUtil;
import seedu.moolah.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteEventCommandParser implements Parser<DeleteEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteEventCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteEventCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE), pe);
        }
    }

}
