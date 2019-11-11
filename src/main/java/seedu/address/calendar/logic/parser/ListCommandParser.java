package seedu.address.calendar.logic.parser;

import seedu.address.calendar.logic.commands.ListCommand;
import seedu.address.calendar.model.event.EventQuery;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

public class ListCommandParser {
    private static final String MESSAGE_INVALID_COMMAND_FORMAT = "Incorrect list command format. %s";
    private static final Prefix[] prefixes = { CliSyntax.PREFIX_START_DAY, CliSyntax.PREFIX_START_MONTH,
            CliSyntax.PREFIX_START_YEAR, CliSyntax.PREFIX_END_DAY, CliSyntax.PREFIX_END_MONTH,
            CliSyntax.PREFIX_END_YEAR };

    ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, prefixes);

        if (ParserUtil.hasMultiplePrefixes(argMultimap, prefixes)) {
            throw new ParseException(ParserUtil.MESSAGE_ARG_DUPLICATED);
        }

        boolean hasDatePrefixes = ParserUtil.arePrefixesPresent(argMultimap, CliSyntax.PREFIX_START_DAY);

        if (!hasDatePrefixes) {
            return new ListCommand();
        }

        try {
            EventQuery eventQuery = ParserUtil.getEventQuery(argMultimap);
            return new ListCommand(eventQuery);
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, e.getMessage()));
        }
    }
}
