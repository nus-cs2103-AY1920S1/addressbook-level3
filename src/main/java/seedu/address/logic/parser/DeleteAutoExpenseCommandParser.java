package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteAutoExpenseCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteAutoExpenseCommand object
 */
public class DeleteAutoExpenseCommandParser implements Parser<DeleteAutoExpenseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAutoExpenseCommand
     * and returns a DeleteAutoExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAutoExpenseCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteAutoExpenseCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAutoExpenseCommand.MESSAGE_USAGE), pe);
        }
    }

}
