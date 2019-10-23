package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RedoCommand object
 */
public class RedoCommandParser implements Parser<RedoCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the RedoCommand
     * and returns an RedoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RedoCommand parse(String args) throws ParseException {
        if (tooManyArgumentsPresent(args)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        }
        return new RedoCommand();
    }

    /**
     * Returns true if there are more arguments than 'redo' in the give {@code ArgumentMultimap}.
     */
    private static boolean tooManyArgumentsPresent(String args) {
        String trimmedRedo = args.trim();
        return trimmedRedo.equals("redo");
    }

}

