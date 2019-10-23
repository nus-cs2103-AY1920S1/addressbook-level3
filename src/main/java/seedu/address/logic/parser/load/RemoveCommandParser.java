package seedu.address.logic.parser.app.load;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.loadcommands.CreateCommand;
import seedu.address.logic.commands.loadcommands.RemoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveCommand object
 */
public class RemoveCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the RemoveCommand
     * and returns an RemoveCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveCommand parse(String name) throws ParseException {
        String trimmedArgs = name.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE));
        }
        return new RemoveCommand(trimmedArgs);
    }
}
