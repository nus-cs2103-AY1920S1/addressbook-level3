package seedu.address.logic.parser.expense;

import seedu.address.logic.commands.expense.EnterDaysViewCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code EnterDaysViewCommand}.
 */
public class EnterDaysViewParser implements Parser<EnterDaysViewCommand> {
    @Override
    public EnterDaysViewCommand parse(String args) throws ParseException {
        return new EnterDaysViewCommand();
    }
}
