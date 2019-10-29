package seedu.address.logic.finance.parser;

import seedu.address.logic.finance.commands.StatsCommand;
import seedu.address.logic.finance.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new StatsCommand object
 */
public class StatsCommandParser implements Parser<StatsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SpendCommand
     * and returns an SpendCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatsCommand parse(String args) throws ParseException {
        return new StatsCommand();
    }

}
