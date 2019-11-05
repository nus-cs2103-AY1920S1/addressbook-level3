package seedu.address.logic.parser.deletecommandparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.deletecommands.DeleteExpenseCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteExpenseCommand object
 */
public class DeleteExpenseCommandParser implements Parser<DeleteExpenseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteExpenseCommand
     * and returns a DeleteExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteExpenseCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteExpenseCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteExpenseCommand.MESSAGE_USAGE), pe);
        }
    }

}
