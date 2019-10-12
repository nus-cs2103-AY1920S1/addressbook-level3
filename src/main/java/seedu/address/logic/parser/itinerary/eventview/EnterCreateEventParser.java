package seedu.address.logic.parser.itinerary.eventview;

import seedu.address.logic.commands.itinerary.events.EnterCreateEventCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Placeholder javadoc.
 */
public class EnterCreateEventParser implements Parser<EnterCreateEventCommand> {
    @Override
    public EnterCreateEventCommand parse(String userInput) throws ParseException {
        return new EnterCreateEventCommand();
    }
}
