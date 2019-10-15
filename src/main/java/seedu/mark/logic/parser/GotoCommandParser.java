package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.GotoCommand;
import seedu.mark.logic.parser.exceptions.ParseException;

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
            Index index = ParserUtil.parseIndex(args);
            return new GotoCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GotoCommand.MESSAGE_USAGE), pe);
        }
    }

}
