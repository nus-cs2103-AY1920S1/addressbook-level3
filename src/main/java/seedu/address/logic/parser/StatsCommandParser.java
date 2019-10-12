package seedu.address.logic.parser;

import seedu.address.logic.commands.StatisticType;
import seedu.address.logic.commands.StatsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new StatsCommand object
 */
public class StatsCommandParser implements Parser<StatsCommand> {

    @Override
    public StatsCommand parse(String userInput) throws ParseException {
        return new StatsCommand("starting","ending", StatisticType.REVENUE);
    }
}
