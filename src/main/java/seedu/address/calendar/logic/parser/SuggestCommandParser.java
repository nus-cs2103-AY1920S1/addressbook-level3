package seedu.address.calendar.logic.parser;

import seedu.address.calendar.logic.commands.SuggestCommand;
import seedu.address.calendar.model.event.EventQuery;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

public class SuggestCommandParser {
    private static final String MESSAGE_INVALID_COMMAND_FORMAT = "Incorrect suggest command format. %s";
    private static final Prefix[] prefixes = { CliSyntax.PREFIX_START_DAY, CliSyntax.PREFIX_START_MONTH,
            CliSyntax.PREFIX_START_YEAR, CliSyntax.PREFIX_END_DAY, CliSyntax.PREFIX_END_MONTH,
            CliSyntax.PREFIX_END_YEAR, CliSyntax.PREFIX_PERIOD };

    SuggestCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, prefixes);

        boolean hasDatePrefixes = ParserUtil.arePrefixesPresent(argMultimap, CliSyntax.PREFIX_START_DAY);

        if (!hasDatePrefixes) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuggestCommand.MESSAGE_USAGE));
        } else if (ParserUtil.hasMultiplePrefixes(argMultimap, prefixes)) {
            throw new ParseException(ParserUtil.MESSAGE_ARG_DUPLICATED);
        }

        EventQuery eventQuery;

        try {
            eventQuery = ParserUtil.getEventQuery(argMultimap);
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, e.getMessage()));
        }

        boolean hasPeriodPrefix = ParserUtil.arePrefixesPresent(argMultimap, CliSyntax.PREFIX_PERIOD);

        if (!hasPeriodPrefix) {
            return new SuggestCommand(eventQuery);
        }

        String minPeriodStr = argMultimap.getValue(CliSyntax.PREFIX_PERIOD)
                .orElseThrow();

        try {
            int minPeriod = Integer.parseInt(minPeriodStr);

            if (minPeriod < 1) {
                throw new ParseException("Minimum period must be positive.");
            }

            return new SuggestCommand(eventQuery, minPeriod);
        } catch (NumberFormatException e) {
            throw new ParseException("Minimum period must be specified using an integer.");
        }
    }
}
