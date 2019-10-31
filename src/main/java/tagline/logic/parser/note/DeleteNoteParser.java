// @@author shiweing
package tagline.logic.parser.note;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import tagline.logic.commands.note.DeleteNoteCommand;
import tagline.logic.parser.Parser;
import tagline.logic.parser.Prompt;
import tagline.logic.parser.exceptions.ParseException;
import tagline.logic.parser.exceptions.PromptRequestException;
import tagline.model.note.NoteId;

/**
 * Parses input arguments and creates a new DeleteNoteCommand object
 */
public class DeleteNoteParser implements Parser<DeleteNoteCommand> {
    public static final String DELETE_NOTE_MISSING_ID_PROMPT = "Please enter the ID of the note to delete.";

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteNoteCommand
     * and returns a DeleteNoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteNoteCommand parse(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            throw new PromptRequestException(Arrays.asList(new Prompt("", DELETE_NOTE_MISSING_ID_PROMPT)));
        }

        try {
            NoteId noteId = NoteParserUtil.parseIndex(args);
            return new DeleteNoteCommand(noteId);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteNoteCommand.MESSAGE_USAGE), pe);
        }
    }
}
