package seedu.address.logic.parser.expense;

import seedu.address.logic.commands.expenditure.EnterCreateExpenditureCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code EnterCreateExpenseCommand}.
 */
public class EnterCreateExpenditureParser implements Parser<EnterCreateExpenditureCommand> {
    @Override
    public EnterCreateExpenditureCommand parse(String args) throws ParseException {
        return new EnterCreateExpenditureCommand();
    }
}
