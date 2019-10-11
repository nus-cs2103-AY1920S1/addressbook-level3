package seedu.address.logic.parser.sidebar;

import seedu.address.logic.commands.sidebar.EnterItineraryPageCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

public class EnterItineraryPageParser implements Parser<EnterItineraryPageCommand> {
    @Override
    public EnterItineraryPageCommand parse(String userInput) throws ParseException {
        return new EnterItineraryPageCommand();
    }
}
