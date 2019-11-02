package seedu.address.calendar.parser;

import seedu.address.calendar.commands.*;
import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.event.Commitment;
import seedu.address.calendar.model.event.EventQuery;
import seedu.address.calendar.model.event.EventType;
import seedu.address.calendar.model.event.Holiday;
import seedu.address.calendar.model.event.Info;
import seedu.address.calendar.model.event.Name;
import seedu.address.calendar.model.event.SchoolBreak;
import seedu.address.calendar.model.event.Trip;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Optional;

class AddCommandParser {
    private static final String MESSAGE_INVALID_COMMAND_FORMAT = "Incorrect add command format. %s";
    private static final Prefix[] prefixes = { CliSyntax.PREFIX_START_DAY, CliSyntax.PREFIX_START_MONTH,
            CliSyntax.PREFIX_START_YEAR, CliSyntax.PREFIX_END_DAY, CliSyntax.PREFIX_END_MONTH,
            CliSyntax.PREFIX_END_YEAR, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_INFO };

    AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, prefixes);

        String preamble = argMultimap.getPreamble();

        if (preamble.equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        String eventTypeStr = preamble.toUpperCase();
        EventType eventType;

        try {
            eventType = EventType.valueOf(eventTypeStr);
        } catch (IllegalArgumentException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_VALID_TYPES));
        }

        if (!ParserUtil.arePrefixesPresent(argMultimap, CliSyntax.PREFIX_START_DAY, CliSyntax.PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } else if (ParserUtil.hasMultiplePrefixes(argMultimap, prefixes)) {
            throw new ParseException(ParserUtil.MESSAGE_ARG_DUPLICATED);
        }

        // assumptions: if no start month/year specified it is the current month/year
        Date startDate = DateParser.parseStartDate(argMultimap, CliSyntax.PREFIX_START_MONTH,
                CliSyntax.PREFIX_START_YEAR, CliSyntax.PREFIX_START_DAY);

        // assumptions: if nothing is specified, it will be the same as those of the start date
        Date endDate = DateParser.parseEndDate(argMultimap, startDate, CliSyntax.PREFIX_END_MONTH,
                CliSyntax.PREFIX_END_YEAR, CliSyntax.PREFIX_END_DAY);

        boolean isValidPeriod = EventQuery.isValidEventTime(startDate, endDate);

        if (!isValidPeriod) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CommandUtil.MESSAGE_DATE_RESTRICTION));
        }

        Name name = new NameParser().parse(argMultimap.getValue(CliSyntax.PREFIX_NAME)).get();
        Optional<Info> info = new InfoParser().parse(argMultimap.getValue(CliSyntax.PREFIX_INFO));

        return parse(eventType, name, startDate, endDate, info);
    }

    AddCommand parse(EventType eventType, Name name, Date startDate, Date endDate, Optional<Info> info) {
        switch (eventType) {
        case COMMITMENT:
            Commitment commitment = new Commitment(name, startDate, endDate, info);
            return new AddCommitmentCommand(commitment);
        case HOLIDAY:
            Holiday holiday = new Holiday(name, startDate, endDate, info);
            return new AddHolidayCommand(holiday);
        case SCHOOL_BREAK:
            SchoolBreak schoolBreak = new SchoolBreak(name, startDate, endDate, info);
            return new AddSchoolBreakCommand(schoolBreak);
        default:
            assert eventType.equals(EventType.TRIP) : "There are only 4 valid types of add commands.";
            Trip trip = new Trip(name, startDate, endDate, info);
            return new AddTripCommand(trip);
        }
    }
}
