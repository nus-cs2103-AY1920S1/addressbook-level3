package seedu.address.logic.parser.bookings;

import seedu.address.logic.commands.bookings.EnterCreateBookingCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code EnterCreateExpenseCommand}.
 */
public class EnterCreateBookingParser implements Parser<EnterCreateBookingCommand> {
    @Override
    public EnterCreateBookingCommand parse(String args) throws ParseException {
        return new EnterCreateBookingCommand();
    }
}
