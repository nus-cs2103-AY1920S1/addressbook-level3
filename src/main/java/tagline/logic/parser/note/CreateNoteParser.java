package tagline.logic.parser.note;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.logic.parser.note.NoteCliSyntax.PREFIX_CONTENT;
import static tagline.logic.parser.note.NoteCliSyntax.PREFIX_TITLE;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import tagline.logic.commands.note.CreateNoteCommand;
import tagline.logic.parser.ArgumentMultimap;
import tagline.logic.parser.ArgumentTokenizer;
import tagline.logic.parser.Parser;
import tagline.logic.parser.Prefix;
import tagline.logic.parser.exceptions.ParseException;
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
    /**
     * Parses the given {@code String} of arguments in the context of the CreateNoteCommand
     * and returns an CreateNoteCommand object for execution.
     * @throws tagline.logic.parser.exceptions.ParseException if the user input does not conform the expected format
     */
    public CreateNoteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_CONTENT);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateNoteCommand.MESSAGE_USAGE));
        }

        NoteId noteId = new NoteId();
        Title title = new Title(argMultimap.getValue(PREFIX_TITLE).orElse(""));
        Content content = new Content(argMultimap.getValue(PREFIX_CONTENT).orElse(""));
        TimeCreated timeCreated = new TimeCreated();
        TimeLastEdited timeLastEdited = new TimeLastEdited(timeCreated.getTime());
        Set<Tag> tags = new HashSet<>(); /* TO UPDATE TAG PARSING WHEN TAG IMPLEMENTED */

        Note note = new Note(noteId, title, content, timeCreated, timeLastEdited, tags);

        return new CreateNoteCommand(note);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
