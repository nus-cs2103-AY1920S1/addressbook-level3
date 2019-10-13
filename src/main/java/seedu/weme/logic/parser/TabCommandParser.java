package seedu.weme.logic.parser;

import seedu.weme.logic.commands.TabCommand;
import seedu.weme.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new TabCommand object.
 */
public class TabCommandParser implements Parser<TabCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the TabCommand
     * and returns a TabCommand object for execution.
     * @throws ParseException if the user input does not provide a valid context.
     */
    @Override
    public TabCommand parse(String userInput) throws ParseException {
        return new TabCommand(ParserUtil.parseContext(userInput));
    }
}
