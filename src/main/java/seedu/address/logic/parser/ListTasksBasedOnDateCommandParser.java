package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.calendar.ListTasksBasedOnDateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.TaskContainsDatePredicate;

/**
 * Parses input arguments and creates a new ListTasksBasedOnDateCommand object
 */
public class ListTasksBasedOnDateCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the ListTasksBasedOnDateCommand
     * and returns a ListTasksBasedOnDateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListTasksBasedOnDateCommand parse(String arg) throws ParseException {
        String trimmedArg = arg.trim();
        if (trimmedArg.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListTasksBasedOnDateCommand.MESSAGE_USAGE));
        }


        return new ListTasksBasedOnDateCommand(new TaskContainsDatePredicate(trimmedArg));
    }
}
