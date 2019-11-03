package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.commands.note.AddNoteCommand;
import seedu.address.logic.commands.note.EditNoteCommand;
import seedu.address.model.note.Note;

/**
 * A utility class for Note.
 */
public class NoteUtil {

    /**
     * Returns an add command string for adding the {@code note}.
     */
    public static String getAddCommand(Note note) {
        return AddNoteCommand.COMMAND_WORD + " " + getNoteDetails(note);
    }

    /**
     * Returns the part of command string for the given {@code note}'s details.
     */
    public static String getNoteDetails(Note note) {
        return PREFIX_TITLE + note.getTitle().title + " " + PREFIX_CONTENT + note.getContent().content;
    }

    /**
     * Returns the part of command string for the given {@code EditNoteDescriptor}'s details.
     */
    public static String getEditNoteDescriptorDetails(EditNoteCommand.EditNoteDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTitle().ifPresent(name -> sb.append(PREFIX_TITLE).append(name.title).append(" "));
        descriptor.getContent().ifPresent(address -> sb.append(PREFIX_CONTENT).append(address.content).append(" "));
        return sb.toString();
    }
}
