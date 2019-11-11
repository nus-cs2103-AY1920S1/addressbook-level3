package seedu.moneygowhere.logic.parser;

import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.moneygowhere.logic.parser.ParserUtil.DEADLINE_INVALID_FAR_BEHIND;
import static seedu.moneygowhere.logic.parser.ParserUtil.DEADLINE_INVALID_TOO_FAR;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.moneygowhere.logic.commands.reminder.AddReminderCommand;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.reminder.Reminder;
import seedu.moneygowhere.model.reminder.ReminderMessage;
import seedu.moneygowhere.model.spending.Date;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddReminderCommandParser implements Parser<AddReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand and returns an AddCommand object
     * for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_MESSAGE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_MESSAGE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));
        }

        String reminderMessage = argMultimap.getValue(PREFIX_MESSAGE).get().trim();

        if (reminderMessage.isEmpty()) {
            throw new ParseException(ReminderMessage.MESSAGE_CONSTRAINTS);
        }

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        ReminderMessage message = ParserUtil.parseMessage(reminderMessage);

        if (date.dateValue.isAfter(LocalDate.now())
                && date.dateValue.getYear() - LocalDate.now().getYear() > ParserUtil.DATE_FAR_FORWARD_RANGE) {
            throw new ParseException(DEADLINE_INVALID_TOO_FAR);
        }

        if (date.dateValue.isBefore(LocalDate.now())) {
            throw new ParseException(DEADLINE_INVALID_FAR_BEHIND);
        }


        Reminder reminder = new Reminder(date, message);

        return new AddReminderCommand(reminder);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
