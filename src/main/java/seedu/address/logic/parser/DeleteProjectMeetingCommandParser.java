package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteProjectMeetingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteProjectMeetingCommand object
 */
public class DeleteProjectMeetingCommandParser implements Parser<DeleteProjectMeetingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteProjectMeetingCommand
     * and returns a DeleteProjectMeetingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteProjectMeetingCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteProjectMeetingCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteProjectMeetingCommand.MESSAGE_USAGE), pe);
        }
    }
}
