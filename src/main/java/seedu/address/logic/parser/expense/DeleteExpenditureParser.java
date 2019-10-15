package seedu.address.logic.parser.expense;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.expenditure.DeleteExpenditureCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses the arguments to return a {@code DeleteExpenditureCommand}.
 */
public class DeleteExpenditureParser implements Parser<DeleteExpenditureCommand> {
    @Override
    public DeleteExpenditureCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteExpenditureCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteExpenditureCommand.MESSAGE_USAGE), pe);
        }
    }
}
