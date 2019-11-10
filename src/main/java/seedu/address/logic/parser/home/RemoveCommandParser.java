// @@author chrischenhui
package seedu.address.logic.parser.home;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.wordbankcommands.RemoveCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveCommand object
 */
public class RemoveCommandParser implements Parser<RemoveCommand> {
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
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));
        }
        return new RemoveCommand(trimmedArgs);
    }
}
