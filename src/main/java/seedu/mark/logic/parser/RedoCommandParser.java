package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.mark.commons.util.StringUtil;
import seedu.mark.logic.commands.RedoCommand;
import seedu.mark.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RedoCommand object
 */
public class RedoCommandParser implements Parser<RedoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RedoCommand
     * and returns a RedoCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public RedoCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()) {
                return new RedoCommand(1);
            }
            if (!StringUtil.isNonZeroUnsignedInteger(trimmedArgs)) {
                throw new ParseException("Step is not a non-zero unsigned integer.");
            }
            int step = Integer.parseInt(trimmedArgs);
            return new RedoCommand(step);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE), pe);
        }
    }

}
