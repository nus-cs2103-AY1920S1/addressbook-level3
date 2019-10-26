package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.EventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.performance.Event;

/**
 * Parse input arguments and creates a new EventCommand object.
 */
public class EventCommandParser implements Parser<EventCommand> {

    @Override
    public EventCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventCommand.MESSAGE_USAGE));
        }

        Event event = new Event(trimmedArgs);

        return new EventCommand(event);
    }

}
