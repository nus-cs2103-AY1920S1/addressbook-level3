package seedu.address.logic.parser;

import seedu.address.logic.commands.GotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.View;

/**
 * Parses input arguments and creates a new GotoCommand object
 */
public class GotoCommandParser implements Parser<GotoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GotoCommand
     * and returns a GotoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GotoCommand parse(String args) throws ParseException {
        try {
            View view = ParserUtil.parseView(args);
            return new GotoCommand(view);
        } catch (ParseException pe) {
            throw pe;
            //          throw new ParseException(
            //          String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
