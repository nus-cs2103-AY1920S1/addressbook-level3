package seedu.address.logic.parser.sidebar;

import seedu.address.logic.commands.sidebar.EnterDayPageCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code EnterDayPageCommand}.
 */
public class EnterDayPageParser implements Parser<EnterDayPageCommand> {
    @Override
    public EnterDayPageCommand parse(String userInput) throws ParseException {
        return new EnterDayPageCommand();
    }
}
