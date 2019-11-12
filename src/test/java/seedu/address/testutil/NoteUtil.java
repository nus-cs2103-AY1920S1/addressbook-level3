package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Set;

import seedu.address.logic.commands.AddNotesCommand;
import seedu.address.logic.commands.EditNoteCommand;
import seedu.address.model.note.Note;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Note.
 */
public class NoteUtil {

    /**
     * Returns an add command string for adding the {@code note}.
     */
    public static String getAddCommand(Note note) {
        return AddNotesCommand.COMMAND_WORD + " " + getNoteDetails(note);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getNoteDetails(Note note) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + note.getTitle().title + " ");
        sb.append(PREFIX_DESCRIPTION + note.getDescription().description + " ");
        sb.append(PREFIX_CONTENT + note.getContent().content + " ");
        note.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditNoteDescriptorDetails(EditNoteCommand.EditNoteDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTitle().ifPresent(title -> sb.append(PREFIX_TITLE).append(title.title).append(" "));
        descriptor.getDescription().ifPresent(description -> sb.append(PREFIX_DESCRIPTION)
                .append(description.description).append(" "));
        descriptor.getContent().ifPresent(content -> sb.append(PREFIX_CONTENT).append(content.content).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
