package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteNoteCommand object
 */
public class DeleteNoteCommandParser implements Parser<DeleteNoteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteNoteCommand
     * and returns a DeleteNoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteNoteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteNoteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteNoteCommand.MESSAGE_USAGE), pe);
        }
    }

}
