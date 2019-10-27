package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DoneCommand object
 */
public class DoneCommandParser implements Parser<DoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DoneCommand
     * and returns a DoneCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DoneCommand parse(String args) throws ParseException {
        try {
            int taskId = ParserUtil.parseId(args);
            return new DoneCommand(taskId);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE), pe);
        }
    }
}
