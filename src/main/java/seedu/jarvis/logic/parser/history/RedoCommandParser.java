package seedu.jarvis.logic.parser.history;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.jarvis.logic.commands.history.RedoCommand;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code RedoCommand} object
 */
public class RedoCommandParser implements Parser<RedoCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RedoCommand}
     * and returns a {@code RedoCommand} object for execution.
     *
     * @throws ParseException If the user input does not conform the expected format.
     */
    @Override
    public RedoCommand parse(String args) throws ParseException {
        int numberOfTimes;
        try {
            numberOfTimes = ParserUtil.parseNonZeroUnsignedInteger(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        }
        return new RedoCommand(numberOfTimes);
    }
}
