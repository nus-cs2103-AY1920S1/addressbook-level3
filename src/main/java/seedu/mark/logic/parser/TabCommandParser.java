package seedu.mark.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.mark.logic.commands.TabCommand;
import seedu.mark.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new TabCommand object.
 */
public class TabCommandParser implements Parser<TabCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TabCommand
     * and returns a TabCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TabCommand parse(String args) throws ParseException {
        requireNonNull(args);

        return new TabCommand(ParserUtil.parseTab(args));
    }
}
