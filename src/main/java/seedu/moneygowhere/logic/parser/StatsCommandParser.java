package seedu.moneygowhere.logic.parser;

import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.List;
import java.util.stream.Stream;

import seedu.moneygowhere.logic.commands.StatsCommand;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.spending.Date;

/**
 * Parses input arguments and creates a new StatsCommand object
 */
public class StatsCommandParser implements Parser<StatsCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the StatsCommand
     * and returns a StatsCommand object
     * for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        if (!argMultimap.getPreamble().isEmpty()) {
            return new StatsCommand();
        } else if (arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            List<Date> datesList = ParserUtil.parseDates(argMultimap.getAllValues(PREFIX_DATE));
            //If there are 2 dates specified.
            if (datesList.size() == 2) {
                Date startDate = datesList.get(0);
                Date endDate = datesList.get(1);
                //if start date is equal to or after end date.
                if (startDate.value.compareTo(endDate.value) <= 0) {
                    return new StatsCommand(startDate, endDate);
                } else {
                    //Error if end date is before start date.
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
                }
            } else {
                //Error if number of dates is not equal to 2.
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
            }
        }
        return new StatsCommand();
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
