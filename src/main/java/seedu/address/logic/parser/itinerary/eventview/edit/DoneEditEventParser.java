package seedu.address.logic.parser.itinerary.eventview.edit;

import seedu.address.logic.commands.itinerary.events.edit.DoneEditEventCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Placeholder javadoc.
 */
public class DoneEditEventParser implements Parser<DoneEditEventCommand> {
    @Override
    public DoneEditEventCommand parse(String userInput) throws ParseException {
        return new DoneEditEventCommand();
    }
}
