package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.mark.commons.util.StringUtil;
import seedu.mark.logic.commands.UndoCommand;
import seedu.mark.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UndoCommand object
 */
public class UndoCommandParser implements Parser<UndoCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UndoCommand
     * and returns a UndoCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public UndoCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()) {
                return new UndoCommand(1);
            }
            if (!StringUtil.isNonZeroUnsignedInteger(trimmedArgs)) {
                throw new ParseException("The number of steps " + trimmedArgs + " is not a non-zero unsigned integer.");
            }
            int step = Integer.parseInt(trimmedArgs);
            return new UndoCommand(step);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE), pe);
        }
    }

}
