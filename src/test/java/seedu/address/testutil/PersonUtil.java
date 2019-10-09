package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.commands.AddNoteCommand;
import seedu.address.logic.commands.EditNoteCommand;
import seedu.address.model.note.Note;

/**
 * A utility class for Note.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code note}.
     */
    public static String getAddCommand(Note note) {
        return AddNoteCommand.COMMAND_WORD + " " + getPersonDetails(note);
    }

    /**
     * Returns the part of command string for the given {@code note}'s details.
     */
    public static String getPersonDetails(Note note) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + note.getTitle().title + " ");
        sb.append(PREFIX_CONTENT + note.getContent().content + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditNoteDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditNoteCommand.EditNoteDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTitle().ifPresent(name -> sb.append(PREFIX_TITLE).append(name.title).append(" "));
        descriptor.getContent().ifPresent(address -> sb.append(PREFIX_CONTENT).append(address.content).append(" "));
        return sb.toString();
    }
}
