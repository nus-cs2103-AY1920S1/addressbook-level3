package seedu.address.logic.parser.bookings.edit;

import seedu.address.logic.commands.bookings.edit.DoneEditBookingsCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code DoneEditTripCommand}.
 */
public class DoneEditBookingParser implements Parser<DoneEditBookingsCommand> {
    @Override
    public DoneEditBookingsCommand parse(String userInput) throws ParseException {
        return new DoneEditBookingsCommand();
    }
}
