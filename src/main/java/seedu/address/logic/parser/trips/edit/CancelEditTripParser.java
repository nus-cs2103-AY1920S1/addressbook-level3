package seedu.address.logic.parser.trips.edit;

import seedu.address.logic.commands.trips.edit.CancelEditTripCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code CancelEditTripCommand}.
 */
public class CancelEditTripParser implements Parser<CancelEditTripCommand> {
    @Override
    public CancelEditTripCommand parse(String userInput) throws ParseException {
        return new CancelEditTripCommand();
    }
}
