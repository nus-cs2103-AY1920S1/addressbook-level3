package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.stream.Stream;

import seedu.address.logic.commands.statistics.StatsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expense.Timestamp;
import seedu.address.model.statistics.Statistics;



/**
 * Parses input arguments and creates a new StatsCommand object
 */
public class StatsCommandParser implements Parser<StatsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StatsCommand
     * and returns an StatsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_START_DATE, PREFIX_END_DATE);
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
        }
        if (hasRepeatedPrefixes(argMultimap, PREFIX_START_DATE, PREFIX_END_DATE)) {
            throw new ParseException(MESSAGE_REPEATED_PREFIX_COMMAND);
        }

        Timestamp startDate = null;
        Timestamp endDate = null;

        boolean isStartPresent = argMultimap.getValue(PREFIX_START_DATE).isPresent();
        boolean isEndPresent = argMultimap.getValue(PREFIX_END_DATE).isPresent();

        if (isStartPresent && isEndPresent) {
            checkStartBeforeEnd(argMultimap);
            startDate = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_START_DATE).get());
            endDate = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_END_DATE).get());
        } else if (isStartPresent) {
            startDate = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_START_DATE).get());
            endDate = startDate.createForwardTimestamp();
        } else if (isEndPresent) {
            endDate = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_END_DATE).get());
            startDate = endDate.createBackwardTimestamp();
        } else {
            endDate = Timestamp.getCurrentTimestamp();
            startDate = endDate.createBackwardTimestamp();
        }

        return new StatsCommand(startDate, endDate);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the StatsCommand
     * Checks that start date is before the end date of the given {@code ArgumentMultimap}
     *
     * @throws ParseException if the detected start date is after the end date
     */
    private void checkStartBeforeEnd(ArgumentMultimap argMultimap) throws ParseException {
        Timestamp startDate = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_START_DATE).get());
        Timestamp endDate = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_END_DATE).get());
        if (endDate.isBefore(startDate)) {
            throw new ParseException(Statistics.MESSAGE_CONSTRAINTS_END_DATE);
        }
    }

    /**
     * Returns true if none of the prefixes are repeated
     * {@code ArgumentMultimap}.
     */
    private static boolean hasRepeatedPrefixes(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return !(Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getAllValues(prefix).size() <= 1));
    }
}
