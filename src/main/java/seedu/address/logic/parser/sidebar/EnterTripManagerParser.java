package seedu.address.logic.parser.sidebar;

import seedu.address.logic.commands.sidebar.EnterTripManagerCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code EnterTripManagerCommand}.
 */
public class EnterTripManagerParser implements Parser<EnterTripManagerCommand> {
    @Override
    public EnterTripManagerCommand parse(String userInput) throws ParseException {
        return new EnterTripManagerCommand();
    }
}
