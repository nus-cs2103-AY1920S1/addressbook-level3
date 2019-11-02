package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.statistics.StatsTrendCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expense.Timestamp;
import seedu.address.model.statistics.Mode;
import seedu.address.model.statistics.Statistics;

/**
 * Parses input arguments and creates a new StatsTrendCommand object
 */
public class StatsTrendCommandParser implements Parser<StatsTrendCommand> {

    public static final List<Prefix> REQUIRED_PREFIXES = Collections.unmodifiableList(List.of(PREFIX_MODE));

    public static final List<Prefix> OPTIONAL_PREFIXES = Collections.unmodifiableList(List.of(
            PREFIX_START_DATE, PREFIX_END_DATE));


    /**
     * Parses the given {@code String} of arguments in the context of the StatsTrendCommand
     * and returns an StatsTrendCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatsTrendCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_MODE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsTrendCommand.MESSAGE_USAGE));
        }

        if (hasRepeatedPrefixes(argMultimap, PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_MODE)) {
            throw new ParseException(MESSAGE_REPEATED_PREFIX_COMMAND);
        }

        Timestamp startDate = null;
        Timestamp endDate = null;

        boolean isStartPresent = argMultimap.getValue(PREFIX_START_DATE).isPresent();
        boolean isEndPresent = argMultimap.getValue(PREFIX_END_DATE).isPresent();
        Mode mode = ParserUtil.parseMode(argMultimap.getValue(PREFIX_MODE).get());

        if (isStartPresent && isEndPresent) {
            checkStartBeforeEnd(argMultimap);
            startDate = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_START_DATE).get());
            endDate = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_END_DATE).get());
            return StatsTrendCommand.createWithBothDates(startDate, endDate, mode);
        } else if (isStartPresent) {

            startDate = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_START_DATE).get());
            return StatsTrendCommand.createOnlyWithStartDate(startDate, mode);
        } else if (isEndPresent) {
            endDate = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_END_DATE).get());
            return StatsTrendCommand.createOnlyWithEndDate(endDate, mode);
        } else {
            return StatsTrendCommand.createWithNoDate(mode);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true at least one prefix have more than to one value
     * {@code ArgumentMultiMap}.
     */
    private static boolean hasRepeatedPrefixes(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return !(Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getAllValues(prefix).size() <= 1));
    }

    /**
     * Parses the given {@code String} of arguments in the context of the StatsTrendCommand
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



}



