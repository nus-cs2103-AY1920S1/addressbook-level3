package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.reminder.ListReminderBasedOnDateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.ReminderContainsDatePredicate;

/**
 * Parses input arguments and creates a new ListTasksBasedOnDateCommand object
 */
public class ListReminderBasedOnDateCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the ListTasksBasedOnDateCommand
     * and returns a ListTasksBasedOnDateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListReminderBasedOnDateCommand parse(String arg) throws ParseException {
        String trimmedArg = arg.trim();
        if (trimmedArg.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListReminderBasedOnDateCommand.MESSAGE_USAGE));
        }


        return new ListReminderBasedOnDateCommand(new ReminderContainsDatePredicate(trimmedArg));
    }
}
