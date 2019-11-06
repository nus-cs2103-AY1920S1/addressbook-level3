package seedu.address.logic.parser.sidebar;

import seedu.address.logic.commands.sidebar.EnterBookingsManagerCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code EnterBookingsManagerCommand}.
 */

public class EnterBookingsParser implements Parser<EnterBookingsManagerCommand> {
    @Override
    public EnterBookingsManagerCommand parse(String userInput) throws ParseException {
        return new EnterBookingsManagerCommand();
    }
}


