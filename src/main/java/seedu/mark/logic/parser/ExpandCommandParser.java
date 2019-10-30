package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.mark.commons.util.StringUtil;
import seedu.mark.logic.commands.ExpandCommand;
import seedu.mark.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExpandCommand object
 */
public class ExpandCommandParser implements Parser<ExpandCommand> {

    @Override
    public ExpandCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()) {
                return new ExpandCommand(1);
            }
            if (!StringUtil.isNonZeroUnsignedInteger(trimmedArgs)) {
                throw new ParseException("LEVELS is not a non-zero unsigned integer.");
            }
            int levels = Integer.parseInt(trimmedArgs);
            return new ExpandCommand(levels);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpandCommand.MESSAGE_USAGE), pe);
        }
    }
}
