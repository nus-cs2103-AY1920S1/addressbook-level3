package seedu.address.logic.parser.expense.edit;

import seedu.address.logic.commands.expenditure.edit.DoneEditExpenditureCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code DoneEditTripCommand}.
 */
public class DoneEditExpenditureParser implements Parser<DoneEditExpenditureCommand> {
    @Override
    public DoneEditExpenditureCommand parse(String userInput) throws ParseException {
        return new DoneEditExpenditureCommand();
    }
}
