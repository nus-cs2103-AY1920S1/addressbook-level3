package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.budget.DeleteExpenseFromBudgetCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * dummy.
 */
public class DeleteExpenseFromBudgetCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteExpenseFromBudgetCommand
     * and returns a DeleteExpenseFromBudgetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteExpenseFromBudgetCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteExpenseFromBudgetCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteExpenseFromBudgetCommand.MESSAGE_USAGE), pe);
        }
    }
}
