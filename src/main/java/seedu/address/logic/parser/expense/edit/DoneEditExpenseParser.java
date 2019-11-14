package seedu.address.logic.parser.expense.edit;

import seedu.address.logic.commands.expense.edit.DoneEditExpenseCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code DoneEditTripCommand}.
 */
public class DoneEditExpenseParser implements Parser<DoneEditExpenseCommand> {
    @Override
    public DoneEditExpenseCommand parse(String userInput) throws ParseException {
        return new DoneEditExpenseCommand();
    }
}
