package seedu.scheduler.logic.parser;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.commands.ScheduleCommand.MESSAGE_USAGE;

import seedu.scheduler.logic.commands.ScheduleCommand;
import seedu.scheduler.logic.parser.exceptions.ParseException;

/**
 * Parses user's input and creates a new ScheduleCommnad object.
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleCommand
     * and returns a ScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ScheduleCommand parse(String userInput) throws ParseException {
        if (!userInput.trim().isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        return new ScheduleCommand();
    }
}
