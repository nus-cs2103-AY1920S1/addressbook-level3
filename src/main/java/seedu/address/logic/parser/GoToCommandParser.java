package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.Mode;
import seedu.address.logic.commands.GoToCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class GoToCommandParser implements Parser<GoToCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GoToCommand parse(String args) throws ParseException {
        String mode = args.trim().toLowerCase();
        switch (mode) {
        case "password":
            return new GoToCommand(Mode.PASSWORD);
        case "file":
            return new GoToCommand(Mode.FILE);
        case "note":
            return new GoToCommand(Mode.NOTE);
        case "card":
            return new GoToCommand(Mode.CARD);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GoToCommand.MESSAGE_USAGE));
        }
    }

}
