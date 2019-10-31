// @@author shiweing
package tagline.logic.parser.note;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import tagline.logic.commands.note.DeleteNoteCommand;
import tagline.logic.parser.Parser;
import tagline.logic.parser.exceptions.ParseException;
import tagline.model.note.NoteId;

/**
 * Parses input arguments and creates a new DeleteNoteCommand object
 */
public class DeleteNoteParser implements Parser<DeleteNoteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteNoteCommand
     * and returns a DeleteNoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteNoteCommand parse(String args) throws ParseException {
        try {
            NoteId noteId = NoteParserUtil.parseIndex(args);
            return new DeleteNoteCommand(noteId);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteNoteCommand.MESSAGE_USAGE), pe);
        }
    }
}
