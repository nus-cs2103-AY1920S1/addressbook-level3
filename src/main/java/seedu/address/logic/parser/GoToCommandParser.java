package seedu.address.logic.parser;

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

        return new GoToCommand(args.trim());
    }

}
