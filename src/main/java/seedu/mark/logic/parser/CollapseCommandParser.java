package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.mark.commons.util.StringUtil;
import seedu.mark.logic.commands.CollapseCommand;
import seedu.mark.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CollapseCommand object
 */
public class CollapseCommandParser implements Parser<CollapseCommand> {

    @Override
    public CollapseCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()) {
                return new CollapseCommand(1);
            }
            if (!StringUtil.isNonZeroUnsignedInteger(trimmedArgs)) {
                throw new ParseException("LEVELS is not a non-zero unsigned integer.");
            }
            int levels = Integer.parseInt(trimmedArgs);
            return new CollapseCommand(levels);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CollapseCommand.MESSAGE_USAGE), pe);
        }
    }
}
