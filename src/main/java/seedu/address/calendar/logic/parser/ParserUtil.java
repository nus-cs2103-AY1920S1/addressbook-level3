package seedu.address.calendar.logic.parser;

import seedu.address.calendar.logic.commands.CommandUtil;
import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.event.EventQuery;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Provides helper functions for the parsing process.
 */
class ParserUtil {
    static final String MESSAGE_ARG_DUPLICATED = "Duplicated arguments detected.";
    static final String MESSAGE_ARG_EXTRA = "Extra/unrecognised arguments or extra characters detected %s.";
    static final String MESSAGE_DATE_INFO_MISMATCH = "Some information that is provided for end date is not "
            + "provided for start date.";
    private static final String EXTRA_ARG_PATTERN = "(\\d*+\\s++.*)|(\\S*+\\s++.*)";
    private static final Pattern EXTRA_ARG_FORMAT = Pattern.compile(EXTRA_ARG_PATTERN);

    /**
     * Detects duplicated prefixes.
     *
     * @param argumentMultimap {@code ArgumentMultiMap} which contains the relevant prefixes entered by user
     * @param prefixes All valid prefixes for the particular command
     * @return {@code true} if a particular prefix is used more than once
     */
    static boolean hasMultiplePrefixes(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
                .anyMatch(prefix -> argumentMultimap.getAllValues(prefix).size() > 1);
    }

    /**
     * Checks if each of the specified prefixes is present in user input.
     *
     * @param argumentMultimap {@code ArgumentMultiMap} which contains the relevant prefixes entered by user
     * @param prefixes All specified prefixes
     * @return {@code true} if all prefixes that are specified can be found in user input
     */
    static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
            .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Checks if there are additional/unexpected user input.
     *
     * @param userInput The specified user input
     * @return {@code true} if additional/unexpected user input is present
     */
    static boolean hasCharInValue(String userInput) {
        Matcher matcher = EXTRA_ARG_FORMAT.matcher(userInput);
        return matcher.matches();
    }

    /**
     * Checks if any start date information is missing but available for end date.
     *
     * @param argumentMultimap {@code ArgumentMultiMap} which contains the relevant prefixes entered by user
     * @return {@code true} if any start date information is missing but available for end date
     */
    static boolean hasMismatchDatePrefixes(ArgumentMultimap argumentMultimap) {
        boolean isDayMismatch = argumentMultimap.getValue(CliSyntax.PREFIX_START_DAY).isEmpty()
                && argumentMultimap.getValue(CliSyntax.PREFIX_END_DAY).isPresent();

        boolean isMonthMismatch = argumentMultimap.getValue(CliSyntax.PREFIX_START_MONTH).isEmpty()
                && argumentMultimap.getValue(CliSyntax.PREFIX_END_MONTH).isPresent();

        boolean isYearMismatch = argumentMultimap.getValue(CliSyntax.PREFIX_START_YEAR).isEmpty()
                && argumentMultimap.getValue(CliSyntax.PREFIX_END_YEAR).isPresent();

        return isDayMismatch || isMonthMismatch || isYearMismatch;
    }

    /**
     * Gets the required {@code EventQuery} instance from user input.
     *
     * @param argMultimap {@code ArgumentMultiMap} which contains the relevant prefixes entered by user
     * @return The required {@code EventQuery} instance
     * @throws ParseException if the user input is invalid
     */
    static EventQuery getEventQuery(ArgumentMultimap argMultimap) throws ParseException {
        if (ParserUtil.hasMismatchDatePrefixes(argMultimap)) {
            throw new ParseException(ParserUtil.MESSAGE_DATE_INFO_MISMATCH);
        }

        Date startDate = DateParser.parseStartDate(argMultimap, CliSyntax.PREFIX_START_MONTH,
                CliSyntax.PREFIX_START_YEAR, CliSyntax.PREFIX_START_DAY);

        Date endDate = DateParser.parseEndDate(argMultimap, startDate, CliSyntax.PREFIX_END_MONTH,
                CliSyntax.PREFIX_END_YEAR, CliSyntax.PREFIX_END_DAY);

        boolean isValidPeriod = EventQuery.isValidEventTime(startDate, endDate);

        if (!isValidPeriod) {
            throw new ParseException(CommandUtil.MESSAGE_DATE_RESTRICTION);
        }

        return new EventQuery(startDate, endDate);
    }
}
