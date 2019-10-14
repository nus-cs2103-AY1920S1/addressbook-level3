package seedu.address.logic.parser;

import seedu.address.logic.commands.MissAppCommand;
import seedu.address.logic.parser.exceptions.ParseException;


import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new MissAppCommand object
 */
public class MissAppCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public MissAppCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MissAppCommand.MESSAGE_USAGE));
        }

        return new MissAppCommand();
    }
}
