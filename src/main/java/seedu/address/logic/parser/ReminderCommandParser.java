package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALENDAR_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALENDAR_REPETITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;

import java.util.stream.Stream;

import seedu.address.logic.commands.EventCommand;
import seedu.address.logic.commands.ReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.DateTime;

import seedu.address.model.calendar.Description;
import seedu.address.model.calendar.Reminder;
import seedu.address.model.calendar.Repetition;

/**
 * Parses input arguments and creates a new ReminderCommand object
 */
public class ReminderCommandParser implements Parser<ReminderCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ReminderCommand
     * and returns an ReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CALENDAR_DESCRIPTION, PREFIX_DATETIME,
                        PREFIX_CALENDAR_REPETITION);

        if (!arePrefixesPresent(argMultimap, PREFIX_CALENDAR_DESCRIPTION, PREFIX_DATETIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventCommand.MESSAGE_USAGE));
        }

        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_CALENDAR_DESCRIPTION).get());
        DateTime dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());
        Repetition repetition = ParserUtil
                .parseRepetition(argMultimap.getValue(PREFIX_CALENDAR_REPETITION).orElse("none"));

        Reminder reminder = new Reminder(description, dateTime, repetition);

        return new ReminderCommand(reminder);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
