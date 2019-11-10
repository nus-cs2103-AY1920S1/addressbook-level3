package seedu.address.logic.parser.expense;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.expense.SortExpensesCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code SortExpensesCommand}.
 */
public class SortExpensesParser implements Parser<SortExpensesCommand> {
    @Override
    public SortExpensesCommand parse(String args) throws ParseException {
        try {
            String property = ParserUtil.parseProperty(args);
            return new SortExpensesCommand(property);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortExpensesCommand.MESSAGE_USAGE), pe);
        }
    }
}
