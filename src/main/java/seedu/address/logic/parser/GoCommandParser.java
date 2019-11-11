package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.GoCommand.HISTORY_TAB;
import static seedu.address.logic.commands.GoCommand.HOME_TAB;
import static seedu.address.logic.commands.GoCommand.STATISTIC_TAB;

import seedu.address.logic.commands.GoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GoCommand object
 */
public class GoCommandParser implements Parser<GoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GoCommand
     * and returns a GoCommand object for execution.
     *
     * @return the parsed command
     * @throws ParseException if the user input does not conform the expected format
     */
    public GoCommand parse(String args) throws ParseException {

        String tabName = args.trim().toLowerCase();
        if (tabName.equals(HOME_TAB)) {
            return new GoCommand(HOME_TAB);
        } else if (tabName.equals(HISTORY_TAB)) {
            return new GoCommand(HISTORY_TAB);
        } else if (tabName.equals(STATISTIC_TAB)) {
            return new GoCommand(STATISTIC_TAB);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GoCommand.MESSAGE_USAGE));
        }
    }

}
