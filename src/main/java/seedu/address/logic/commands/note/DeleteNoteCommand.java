package seedu.address.logic.commands.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.DELETE;
import static seedu.address.commons.core.Messages.MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_NOTE;
import static seedu.address.commons.core.Messages.MESSAGE_HIT_ENTER_TO_DELETE;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.NoteCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Note;

/**
 * Deletes a note identified using it's displayed index from the note list.
 */
public class DeleteNoteCommand extends Command {

    public static final String COMMAND_WORD = DELETE;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the note identified by the index number used in the displayed note list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_NOTE_SUCCESS = "Deleted Note: %1$s";

    public static boolean isSure = false;

    // negative marked index to prevent access
    private static int markedIndex = -1;

    private final Index targetIndex;

    public DeleteNoteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Note> lastShownList = model.getFilteredNoteList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX);
        }

        Note noteToDelete = lastShownList.get(targetIndex.getZeroBased());
        NoteCommandResult commandResult = new NoteCommandResult("");
        if (!isSure) {
            isSure = true;
            // one prompt for index
            markedIndex = this.targetIndex.getOneBased();
            throw new CommandException(MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_NOTE
                    + "\n" + noteToDelete
                    + "\n" + MESSAGE_HIT_ENTER_TO_DELETE);
        }
        if (isSure && markedIndex == this.targetIndex.getOneBased()) {
            // if this was marked
            // this is to prevent calling delete 1 then
            // calling delete 2
            // user is forced to delete the same index twice in a row.
            model.deleteNote(noteToDelete);
            isSure = false;
            markedIndex = -1; // reset to -1 to prevent wrong access
            commandResult = new NoteCommandResult(String.format(
                    MESSAGE_DELETE_NOTE_SUCCESS, noteToDelete));
        }
        if (isSure) {
            // user is sure he wants to delete but changed the index
            markedIndex = this.targetIndex.getOneBased();
            throw new CommandException(MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_NOTE
                    + "\n" + noteToDelete
                    + "\n" + MESSAGE_HIT_ENTER_TO_DELETE);
        }
        return commandResult;    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteNoteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteNoteCommand) other).targetIndex)); // state check
    }
}
