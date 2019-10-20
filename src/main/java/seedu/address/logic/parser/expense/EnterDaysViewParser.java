package seedu.address.logic.parser.expense;

import seedu.address.logic.commands.expenditure.EnterDaysViewCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

public class EnterDaysViewParser implements Parser<EnterDaysViewCommand> {
    @Override
    public EnterDaysViewCommand parse(String args) throws ParseException {
        return new EnterDaysViewCommand();
    }
}
