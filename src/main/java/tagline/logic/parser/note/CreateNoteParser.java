// @@author shiweing
package tagline.logic.parser.note;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.logic.parser.note.NoteCliSyntax.PREFIX_CONTENT;
import static tagline.logic.parser.note.NoteCliSyntax.PREFIX_TITLE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import tagline.logic.commands.note.CreateNoteCommand;
import tagline.logic.parser.ArgumentMultimap;
import tagline.logic.parser.ArgumentTokenizer;
import tagline.logic.parser.Parser;
import tagline.logic.parser.Prompt;
import tagline.logic.parser.exceptions.ParseException;
import tagline.logic.parser.exceptions.PromptRequestException;
import tagline.model.note.Content;
import tagline.model.note.Note;
import tagline.model.note.NoteId;
import tagline.model.note.TimeCreated;
import tagline.model.note.TimeLastEdited;
import tagline.model.note.Title;
import tagline.model.tag.Tag;

/**
 * Parses input arguments and creates a new CreateNoteCommand object
 */
public class CreateNoteParser implements Parser<CreateNoteCommand> {
    public static final String CREATE_NOTE_MISSING_CONTENT_PROMPT = "Please enter the content of the note.";

    /**
     * Parses the given {@code String} of arguments in the context of the CreateNoteCommand
     * and returns an CreateNoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateNoteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_CONTENT);

        checkArguments(argMultimap);

        NoteId noteId = new NoteId();
        Title title = NoteParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).orElse(""));
        Content content = new Content(argMultimap.getValue(PREFIX_CONTENT).orElse(""));
        TimeCreated timeCreated = new TimeCreated();
        TimeLastEdited timeLastEdited = new TimeLastEdited(timeCreated.getTime());
        Set<Tag> tags = new HashSet<>(); /* TO UPDATE TAG PARSING WHEN TAG IMPLEMENTED */

        if (!Note.isValidNote(title.value, content.value)) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS);
        }
        Note note = new Note(noteId, title, content, timeCreated, timeLastEdited, tags);

        return new CreateNoteCommand(note);
    }

    /**
     * Check for the validity of arguments in {@code argMultimap}
     * E.g. There should be no preamble.
     *      There should be at most one usage of title and content.
     *      Title and content should not be both empty.
     */
    private void checkArguments(ArgumentMultimap argMultimap) throws ParseException {
        // ensure preamble empty
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateNoteCommand.MESSAGE_USAGE));
        }

        // ensure singular prefixes are only used once
        NoteParserUtil.checkSinglePrefixUsage(argMultimap, PREFIX_TITLE, PREFIX_CONTENT);

        // check if both title and content empty
        if (!NoteParserUtil.anyPrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_CONTENT)) {
            throw new PromptRequestException(Arrays.asList(new Prompt(PREFIX_CONTENT.getPrefix(),
                    CREATE_NOTE_MISSING_CONTENT_PROMPT)));
        }
    }
}
