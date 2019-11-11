package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.DisplayAssignmentListCommand;
import seedu.tarence.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DisplayAssignmentListCommand object.
 */
public class DisplayAssignmentListCommandParser extends CommandParser<DisplayAssignmentListCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DisplayAssignmentListCommand
     * and returns a DisplayAssignmentListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplayAssignmentListCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DisplayAssignmentListCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayAssignmentListCommand.MESSAGE_USAGE), pe);
        }
    }
}
