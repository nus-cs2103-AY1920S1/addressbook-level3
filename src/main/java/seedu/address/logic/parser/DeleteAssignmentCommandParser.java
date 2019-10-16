package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteAssignmentCommand object
 */
public class DeleteAssignmentCommandParser implements Parser<DeleteAssignmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAssignmentCommand
     * and returns a DeleteAssignmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAssignmentCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteAssignmentCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAssignmentCommand.MESSAGE_USAGE), pe);
        }
    }

}
