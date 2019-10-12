package seedu.address.logic.parser.itinerary.eventview.edit;

import seedu.address.logic.commands.itinerary.events.edit.CancelEditEventCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Placeholder javadoc.
 */
public class CancelEditEventParser implements Parser<CancelEditEventCommand> {
    @Override
    public CancelEditEventCommand parse(String userInput) throws ParseException {
        return new CancelEditEventCommand();
    }
}
