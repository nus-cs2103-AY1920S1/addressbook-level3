package seedu.guilttrip.logic.parser.deletecommandparsers;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.commands.deletecommands.DeleteWishCommand;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteWishCommand object
 */
public class DeleteWishCommandParser implements Parser<DeleteWishCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteWishCommand
     * and returns a DeleteWishCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteWishCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteWishCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteWishCommand.MESSAGE_USAGE), pe);
        }
    }

}
