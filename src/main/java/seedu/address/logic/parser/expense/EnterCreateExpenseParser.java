package seedu.address.logic.parser.expense;

import seedu.address.logic.commands.expense.EnterCreateExpenseCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code EnterCreateExpenseCommand}.
 */
public class EnterCreateExpenseParser implements Parser<EnterCreateExpenseCommand> {
    @Override
    public EnterCreateExpenseCommand parse(String args) throws ParseException {
        return new EnterCreateExpenseCommand();
    }
}
