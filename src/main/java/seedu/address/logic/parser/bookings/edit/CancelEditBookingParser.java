package seedu.address.logic.parser.bookings.edit;

import seedu.address.logic.commands.bookings.edit.CancelEditBookingsCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code CancelEditExpenditureCommand}.
 */
public class CancelEditBookingParser implements Parser<CancelEditBookingsCommand> {
    @Override
    public CancelEditBookingsCommand parse(String userInput) throws ParseException {
        return new CancelEditBookingsCommand();
    }
}
