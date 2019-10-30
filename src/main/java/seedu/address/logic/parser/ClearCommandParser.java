package seedu.address.logic.parser;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClearCommand object
 */
public class ClearCommandParser implements Parser<ClearCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ClearCommand
     * and returns a ClearCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClearCommand parse(String args) throws ParseException {
        if (!hasConfirmation(args)) {
            throw new ParseException(ClearCommand.MESSAGE_USAGE);
        }

        return new ClearCommand();
    }

    private boolean hasConfirmation(String args) {
        String trimmedStr = args.trim();
        return trimmedStr.equals(ClearCommand.CONFIRMATION_WORD);
    }
}
