package seedu.address.logic.parser.statistics;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.statistics.GetOverviewqCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GetStatisticsCommand object.
 */
public class GetOverviewqCommandParser implements Parser<GetOverviewqCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GetStatisticsCommand
     * and returns an GetStatisticsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public GetOverviewqCommand parse(String args) {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);
        List<String> dates = new ArrayList<>();

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            dates = argMultimap.getAllValues(PREFIX_DATE);
        }
        return new GetOverviewqCommand(dates);
    }
}
