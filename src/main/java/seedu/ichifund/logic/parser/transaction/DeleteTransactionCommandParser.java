package seedu.ichifund.logic.parser.transaction;

import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.ichifund.commons.core.index.Index;
import seedu.ichifund.logic.commands.transaction.DeleteTransactionCommand;
import seedu.ichifund.logic.parser.Parser;
import seedu.ichifund.logic.parser.ParserUtil;
import seedu.ichifund.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteTransactionCommand object
 */
public class DeleteTransactionCommandParser implements Parser<DeleteTransactionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTransactionCommand
     * and returns a DeleteTransactionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTransactionCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteTransactionCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTransactionCommand.MESSAGE_USAGE), pe);
        }
    }

}
