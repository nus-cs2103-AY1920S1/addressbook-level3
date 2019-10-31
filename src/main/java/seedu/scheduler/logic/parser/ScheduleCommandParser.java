package seedu.scheduler.logic.parser;

import seedu.scheduler.commons.core.Messages;
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
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }
        return new ScheduleCommand();
    }
}
