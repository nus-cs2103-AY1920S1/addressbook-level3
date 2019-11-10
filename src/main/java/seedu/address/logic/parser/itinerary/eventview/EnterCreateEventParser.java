package seedu.address.logic.parser.itinerary.eventview;

import seedu.address.logic.commands.itinerary.events.EnterCreateEventCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the given user input {@code String}
 * and returns an EnterCreateEventCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class EnterCreateEventParser implements Parser<EnterCreateEventCommand> {
    @Override
    public EnterCreateEventCommand parse(String userInput) throws ParseException {
        return new EnterCreateEventCommand();
    }
}
