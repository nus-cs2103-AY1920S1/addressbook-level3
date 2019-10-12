package seedu.address.logic.parser.trips.edit;

import seedu.address.logic.commands.trips.edit.DoneEditTripCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code DoneEditTripCommand}.
 */
public class DoneEditTripParser implements Parser<DoneEditTripCommand> {
    @Override
    public DoneEditTripCommand parse(String userInput) throws ParseException {
        return new DoneEditTripCommand();
    }
}
