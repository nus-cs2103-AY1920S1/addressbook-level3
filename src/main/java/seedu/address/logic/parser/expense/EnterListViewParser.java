package seedu.address.logic.parser.expense;

import seedu.address.logic.commands.expense.EnterListViewCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code EnterDaysViewCommand}.
 */
public class EnterListViewParser implements Parser<EnterListViewCommand> {
    @Override
    public EnterListViewCommand parse(String args) throws ParseException {
        return new EnterListViewCommand();
    }
}
