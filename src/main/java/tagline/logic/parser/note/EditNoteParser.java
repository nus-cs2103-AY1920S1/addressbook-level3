// @@author shiweing
package tagline.logic.parser.note;

import static java.util.Objects.requireNonNull;
import static tagline.logic.parser.ParserUtil.anyPrefixesPresent;
import static tagline.logic.parser.note.NoteCliSyntax.PREFIX_CONTENT;
import static tagline.logic.parser.note.NoteCliSyntax.PREFIX_TITLE;

import java.util.Collections;

import tagline.logic.commands.note.EditNoteCommand;
import tagline.logic.commands.note.EditNoteCommand.EditNoteDescriptor;
import tagline.logic.parser.ArgumentMultimap;
import tagline.logic.parser.ArgumentTokenizer;
import tagline.logic.parser.Parser;
import tagline.logic.parser.Prompt;
import tagline.logic.parser.exceptions.ParseException;
import tagline.logic.parser.exceptions.PromptRequestException;
import tagline.model.note.Content;
import tagline.model.note.NoteId;

/**
 * Parses input arguments and creates a new EditNoteCommand object
 */
public class EditNoteParser implements Parser<EditNoteCommand> {
    public static final String EDIT_NOTE_MISSING_ID_PROMPT_STRING = "Please enter the ID of the note to edit.";

    /**
     * Parses the given {@code String} of arguments in the context of the EditNoteCommand
     * and returns an EditNoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditNoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_CONTENT);

        checkArguments(argMultimap);

        NoteId noteId = NoteParserUtil.parseIndex(argMultimap.getPreamble());

        EditNoteDescriptor editNoteDescriptor = new EditNoteDescriptor();
        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            editNoteDescriptor.setTitle(NoteParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get()));
        }
        if (argMultimap.getValue(PREFIX_CONTENT).isPresent()) {
            editNoteDescriptor.setContent(new Content(argMultimap.getValue(PREFIX_CONTENT).get()));
        }

        if (!editNoteDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditNoteCommand.MESSAGE_NOT_EDITED);
        }

        return new EditNoteCommand(noteId, editNoteDescriptor);
    }

    /**
     * Check for the validity of arguments in {@code argMultimap}
     * E.g. There should be at most one usage of title and content.
     */
    private void checkArguments(ArgumentMultimap argMultimap) throws ParseException {
        // missing index but has edits
        if (argMultimap.getPreamble().isEmpty() && anyPrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_CONTENT)) {
            throw new PromptRequestException(Collections.singletonList(
                    new Prompt("", EDIT_NOTE_MISSING_ID_PROMPT_STRING)));
        }

        // check at most only one usage of provided prefixes
        NoteParserUtil.checkSinglePrefixUsage(argMultimap, PREFIX_TITLE, PREFIX_CONTENT);
    }
}

