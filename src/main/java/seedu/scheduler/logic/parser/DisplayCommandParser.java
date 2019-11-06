package seedu.scheduler.logic.parser;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.commands.DisplayCommand.MESSAGE_USAGE;

import seedu.scheduler.logic.commands.DisplayCommand;
import seedu.scheduler.logic.parser.exceptions.ParseException;

/**
 * Parse input arguments and create a new DisplayCommand object.
 */
public class DisplayCommandParser implements Parser<DisplayCommand> {

    @Override
    public DisplayCommand parse(String args) throws ParseException {
        args.trim();
        if (args.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE)
            );
        }
        return new DisplayCommand(args);
    }
}
