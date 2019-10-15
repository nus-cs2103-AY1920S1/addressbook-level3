package seedu.address.logic.parser.sidebar;

import seedu.address.logic.commands.sidebar.EnterExpenseManagerCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code EnterDayPageCommand}.
 */
public class EnterExpenseManagerParser implements Parser<EnterExpenseManagerCommand> {
    @Override
    public EnterExpenseManagerCommand parse(String userInput) throws ParseException {
        return new EnterExpenseManagerCommand();
    }
}
