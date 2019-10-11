package seedu.address.logic.parser.sidebar;

import seedu.address.logic.commands.sidebar.EnterDayPageCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

public class EnterDayPageParser implements Parser<EnterDayPageCommand> {
    @Override
    public EnterDayPageCommand parse(String userInput) throws ParseException {
        return new EnterDayPageCommand();
    }
}
