// @@author shiweing
package tagline.logic.commands.note;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import tagline.commons.core.Messages;
import tagline.logic.commands.CommandResult;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.Model;
import tagline.model.note.Note;
import tagline.model.note.NoteId;

/**
 * Deletes a note identified using it's index.
 */
public class DeleteNoteCommand extends NoteCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_KEY + " " + COMMAND_WORD
            + ": Deletes the note identified by the note index number.\n"
            + "Parameters: NOTE_ID (must be a positive integer)\n"
            + "Example: " + COMMAND_KEY + " " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Delete note: %1$s";

    private final NoteId targetId;

    public DeleteNoteCommand(NoteId noteId) {
        requireNonNull(noteId);
        targetId = noteId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<Note> noteFound = model.findNote(targetId);

        if (noteFound.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTE_INDEX);
        }

        Note noteToDelete = noteFound.get();

        model.deleteNote(noteToDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, noteToDelete), CommandResult.ViewType.NOTE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteNoteCommand // instanceof handles nulls
                && targetId.equals(((DeleteNoteCommand) other).targetId)); // state check
    }
}
