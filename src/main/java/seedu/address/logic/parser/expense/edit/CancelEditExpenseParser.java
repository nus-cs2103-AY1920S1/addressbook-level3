package seedu.address.logic.parser.expense.edit;

import seedu.address.logic.commands.expense.edit.CancelEditExpenseCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code CancelEditExpenseCommand}.
 */
public class CancelEditExpenseParser implements Parser<CancelEditExpenseCommand> {
    @Override
    public CancelEditExpenseCommand parse(String userInput) throws ParseException {
        return new CancelEditExpenseCommand();
    }
}
