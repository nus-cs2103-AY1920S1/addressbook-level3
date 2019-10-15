package seedu.address.logic.parser.expense.edit;

import seedu.address.logic.commands.expenditure.edit.CancelEditExpenditureCommand;
import seedu.address.logic.commands.trips.edit.CancelEditTripCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code CancelEditExpenditureCommand}.
 */
public class CancelEditExpenditureParser implements Parser<CancelEditExpenditureCommand> {
    @Override
    public CancelEditExpenditureCommand parse(String userInput) throws ParseException {
        return new CancelEditExpenditureCommand();
    }
}
