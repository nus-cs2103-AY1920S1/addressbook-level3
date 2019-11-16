package seedu.ifridge.logic.parser.shoppinglist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.logic.commands.shoppinglist.DeleteShoppingCommand;
import seedu.ifridge.logic.parser.Parser;
import seedu.ifridge.logic.parser.ParserUtil;
import seedu.ifridge.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteShoppingCommand object
 */
public class DeleteShoppingCommandParser implements Parser<DeleteShoppingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteShoppingCommand
     * and returns a DeleteShoppingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteShoppingCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteShoppingCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteShoppingCommand.MESSAGE_USAGE), pe);
        }
    }

}

