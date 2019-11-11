package tagline.testutil.note;

import static tagline.logic.parser.note.NoteCliSyntax.PREFIX_CONTENT;
import static tagline.logic.parser.note.NoteCliSyntax.PREFIX_TAG;
import static tagline.logic.parser.note.NoteCliSyntax.PREFIX_TITLE;

import tagline.logic.commands.note.CreateNoteCommand;
import tagline.model.note.Note;
import tagline.model.tag.Tag;

/**
 * A utility class for Note.
 */
public class NoteUtil {

    /**
     * Returns an add command string for adding the {@code note}.
     */
    public static String getCreateCommand(Note note) {
        return CreateNoteCommand.COMMAND_WORD + " " + getNoteDetails(note);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getNoteDetails(Note note) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + note.getTitle().value + " ");
        sb.append(PREFIX_CONTENT + note.getContent().value + " ");

        for (Tag tag : note.getTags()) {
            sb.append(PREFIX_TAG + tag.toString() + " ");
        }
        return sb.toString();
    }
}
