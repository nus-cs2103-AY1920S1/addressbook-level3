package seedu.weme.logic.parser.commandparser.generalcommandparser;

import seedu.weme.logic.commands.generalcommand.TabCommand;
import seedu.weme.logic.parser.Parser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.parser.util.ParserUtil;

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
        return new TabCommand(ParserUtil.parseTab(userInput));
    }
}
