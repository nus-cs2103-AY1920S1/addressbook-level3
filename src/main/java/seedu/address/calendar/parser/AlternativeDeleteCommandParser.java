package seedu.address.calendar.parser;

import seedu.address.calendar.commands.*;
import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.event.*;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

public class AlternativeDeleteCommandParser {
    private static final Prefix[] prefixes = { CliSyntax.PREFIX_START_DAY, CliSyntax.PREFIX_START_MONTH,
            CliSyntax.PREFIX_START_YEAR, CliSyntax.PREFIX_END_DAY, CliSyntax.PREFIX_END_MONTH,
            CliSyntax.PREFIX_END_YEAR, CliSyntax.PREFIX_NAME };

    DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, prefixes);

        String preamble = argMultimap.getPreamble();

        String eventTypeStr = preamble.toUpperCase();
        EventType eventType = EventType.valueOf(eventTypeStr);

        // assumptions: if no start month/year specified it is the current month/year
        Date startDate = DateParser.parseStartDate(argMultimap, CliSyntax.PREFIX_START_MONTH,
                CliSyntax.PREFIX_START_YEAR, CliSyntax.PREFIX_START_DAY);

        // assumptions: if nothing is specified, it will be the same as those of the start date
        Date endDate = DateParser.parseEndDate(argMultimap, startDate, CliSyntax.PREFIX_END_MONTH,
                CliSyntax.PREFIX_END_YEAR, CliSyntax.PREFIX_END_DAY);

        EventQuery eventQuery = new EventQuery(startDate, endDate);

        return new AlternativeDeleteCommand(eventQuery);
    }
}
