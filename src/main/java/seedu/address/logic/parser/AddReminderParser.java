package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_TIME;

import java.util.TreeSet;
import java.util.stream.Stream;

import seedu.address.logic.commands.reminder.AddReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDescription;
import seedu.address.model.reminder.ReminderTime;

/**
 * Represents the ability to add a reminder for a task.
 * Might disable this feature if reminders should not be created individually
 */

public class AddReminderParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMINDER_DESCRIPTION, PREFIX_REMINDER_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_REMINDER_DESCRIPTION, PREFIX_REMINDER_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));
        }

        ReminderDescription reminderDescription = ParserUtil.parseReminderDescription(argMultimap
                .getValue(PREFIX_REMINDER_DESCRIPTION).get());
        TreeSet<ReminderTime> reminderTimeSet = ParserUtil.parseReminderTimes(argMultimap
                .getAllValues(PREFIX_REMINDER_TIME));

        Reminder reminder = new Reminder(reminderDescription, reminderTimeSet);

        return new AddReminderCommand(reminder);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
