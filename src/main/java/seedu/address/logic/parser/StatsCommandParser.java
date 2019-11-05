package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_INPUT_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STAT_TYPE;

import java.util.Calendar;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.statisticcommand.StatisticType;
import seedu.address.logic.commands.statisticcommand.StatsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.statistic.StatsParseUtil;

/**
 * Parses input arguments and creates a new StatsCommand object
 */
public class StatsCommandParser implements Parser<StatsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StatsCommand
     * and returns an StatsCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public StatsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_STAT_TYPE, PREFIX_STARTING_DATE, PREFIX_ENDING_DATE);

        //date arguments are optional
        if (!arePrefixesPresent(argMultiMap, PREFIX_STAT_TYPE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
        }

        StatisticType type = ParserUtil.parseStatsType(argMultiMap.getValue(PREFIX_STAT_TYPE).get());

        Optional<String> startingDateValue = argMultiMap.getValue(PREFIX_STARTING_DATE);
        Optional<String> endingDateValue = argMultiMap.getValue(PREFIX_ENDING_DATE);

        //if one date value is present and not the other
        if ((startingDateValue.isEmpty() && endingDateValue.isPresent())
                | (startingDateValue.isPresent() && endingDateValue.isEmpty())) {
            throw new ParseException(String.format(
                    Messages.OPTIONAL_DATE_MESSAGE_CONSTRAINTS,
                    StatsCommand.NON_DEFAULT_MESSAGE_USAGE));
        }
        if (startingDateValue.isPresent() && endingDateValue.isPresent()) {
            Calendar startingDate = ParserUtil.parseDateCalendar(
                    argMultiMap.getValue(PREFIX_STARTING_DATE).get());
            Calendar endingDate = ParserUtil.parseDateCalendar(
                    argMultiMap.getValue(PREFIX_ENDING_DATE).get());

            if (startingDate.compareTo(endingDate) > 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_DATE_INPUT_FORMAT,
                        StatsCommand.MESSAGE_USAGE));
            }
            switch (type) {
            case COST:
                return new StatsCommand(startingDate, endingDate, StatisticType.COST);
            case PROFIT:
                return new StatsCommand(startingDate, endingDate, StatisticType.PROFIT);
            case REVENUE:
                return new StatsCommand(startingDate, endingDate, StatisticType.REVENUE);
            default:
                throw new ParseException("Wrong Statistic type for normal calculation mode, "
                        + "types here only include:\n"
                        + "profit, cost, revenue");
            }
        } else {
            switch (type) {
            case COST:
                return new StatsCommand(StatsParseUtil.MIN_DATE, StatsParseUtil.MAX_DATE, StatisticType.COST);
            case PROFIT:
                return new StatsCommand(StatsParseUtil.MIN_DATE, StatsParseUtil.MAX_DATE, StatisticType.PROFIT);
            case REVENUE:
                return new StatsCommand(StatsParseUtil.MIN_DATE, StatsParseUtil.MAX_DATE, StatisticType.REVENUE);
            default:
                throw new ParseException("Wrong Statistic type for no date calculation mode, "
                        + "default types here only include:\n"
                        + "profit, cost, revenue");
            }
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
