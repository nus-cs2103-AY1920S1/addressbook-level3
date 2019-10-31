package tagline.logic.commands.note;

import static java.util.Objects.requireNonNull;
import static tagline.logic.parser.note.NoteCliSyntax.PREFIX_TAG;
import static tagline.model.note.NoteModel.PREDICATE_SHOW_ALL_NOTES;

import java.util.List;
import java.util.Optional;

import tagline.commons.core.Messages;
import tagline.logic.commands.CommandResult;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.Model;
import tagline.model.note.Note;
import tagline.model.note.NoteId;
import tagline.model.tag.Tag;

/**
 * Untags a note from several tags.
 */
public class UntagNoteCommand extends NoteCommand {
    public static final String COMMAND_WORD = "untag";

    public static final String MESSAGE_UNTAG_NOTE_SUCCESS = "Untagged Note: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Untags a note identified "
            + "by the note index number from several tags."
            + "Parameters: NOTE_ID (must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG ]+ \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + " #tagline ";

    private final NoteId noteId;
    private final List<Tag> tags;

    public UntagNoteCommand(NoteId noteId, List<Tag> tags) {
        requireNonNull(noteId);
        requireNonNull(tags);

        assert (tags.size() > 0) : "At least one tag to be provided.";

        this.noteId = noteId;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check for invalid note id
        Optional<Note> noteFound = model.findNote(noteId);

        if (noteFound.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTE_INDEX);
        }

        Note targetNote = noteFound.get();

        for (Tag tag : tags) {
            Tag registeredTag = model.createOrFindTag(tag);

            model.untagNote(targetNote, registeredTag);
        }

        model.updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
        return new CommandResult(String.format(MESSAGE_UNTAG_NOTE_SUCCESS, targetNote), CommandResult.ViewType.NOTE);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UntagNoteCommand)) {
            return false;
        }

        // state check
        UntagNoteCommand otherCommand = (UntagNoteCommand) other;
        return noteId.equals(otherCommand.noteId) && tags.equals(otherCommand.tags);
    }
}
