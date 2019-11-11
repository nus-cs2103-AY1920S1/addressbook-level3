package seedu.moneygowhere.logic.parser;

import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.moneygowhere.logic.commands.ReminderCommand;
import seedu.moneygowhere.logic.commands.reminder.AddReminderCommand;
import seedu.moneygowhere.logic.commands.reminder.DeleteReminderCommand;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ReminderCommand object
 */
public class ReminderCommandParser implements Parser<ReminderCommand> {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand and returns an AddCommand object
     * for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReminderCommand parse(String args) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
        }

        final String secondaryCommandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        if (secondaryCommandWord.equals(AddReminderCommand.COMMAND_WORD)) {
            return new AddReminderCommandParser().parse(arguments);
        } else if (secondaryCommandWord.equals(DeleteReminderCommand.COMMAND_WORD)) {
            return new DeleteReminderCommandParser().parse(arguments);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
        }
    }
}
