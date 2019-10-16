package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.commands.InfoCommand.MULTIPLE_COMMAND_ENTERED_MESSAGE;

import seedu.savenus.logic.commands.InfoCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;

/**
 * Parses the input for command information and create a new InfoCommand object.
 */
public class InfoCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the InfoCommand
     * and returns a InfoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public InfoCommand parse(String args) throws ParseException {

        // To prevent user from writing multiple commands
        String[] argsArray = args.split(" ");
        if (argsArray.length > 2) {
            throw new ParseException(
                    String.format(MULTIPLE_COMMAND_ENTERED_MESSAGE, InfoCommand.MESSAGE_USAGE)
            );
        }

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, InfoCommand.MESSAGE_USAGE)
            );
        }
        return new InfoCommand(trimmedArgs);
    }
}
