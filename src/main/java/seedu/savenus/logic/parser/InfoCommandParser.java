package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.savenus.logic.commands.InfoCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;

/**
 * Parses the input for command information and create a new InfoCommand object.
 */
public class InfoCommandParser {

    public InfoCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, InfoCommand.MESSAGE_USAGE)
            );
        }
        return new InfoCommand(trimmedArgs);
    }
}
