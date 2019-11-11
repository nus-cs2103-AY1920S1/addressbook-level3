package seedu.address.logic.commands.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.DELETE;
import static seedu.address.commons.core.Messages.MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_NOTE;
import static seedu.address.commons.core.Messages.MESSAGE_CONFIRM_DELETE;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandHistory;
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

    private static final Logger logger = LogsCenter.getLogger(DeleteNoteCommand.class);

    private final Index targetIndex;

    /**
     * The successfulDeletionOnPreviousCommand is to prevent the user from calling, for instance,
     * 'delete 1' twice in a row and not get a prompt.
     */
    private static boolean successfulDeletionOnPreviousCommand = false;

    public DeleteNoteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    public Index getTargetIndex() {
        return targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Note> lastShownList = model.getFilteredNoteList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX);
        }

        Note noteToDelete = lastShownList.get(targetIndex.getZeroBased());
        NoteCommandResult commandResult = new NoteCommandResult ((MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_NOTE
                + "\n" + noteToDelete
                + "\n" + String.format(MESSAGE_CONFIRM_DELETE, this.targetIndex.getOneBased())));

        if (CommandHistory.getLastCommand().isPresent()) {
            if (CommandHistory.getLastCommand().get() instanceof DeleteNoteCommand) {
                if (((DeleteNoteCommand) CommandHistory.getLastCommand().get()).getTargetIndex()
                        .equals(this.targetIndex)) {
                    int noteListSize = model.getFilteredNoteList().size();

                    // correct. allow delete
                    model.deleteNote(noteToDelete);

                    // item has been deleted, set this to true
                    successfulDeletionOnPreviousCommand = true;

                    assert (model.getFilteredNoteList().size() - noteListSize == -1);
                    logger.info("Current list size: " + model.getFilteredNoteList().size());

                    commandResult = new NoteCommandResult(String.format(MESSAGE_DELETE_NOTE_SUCCESS, noteToDelete));
                } else {
                    // nothing has been deleted, set back to false
                    successfulDeletionOnPreviousCommand = false;
                }
            } else {
                successfulDeletionOnPreviousCommand = false;
            }
        } else {
            successfulDeletionOnPreviousCommand = false;
        }
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteNoteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteNoteCommand) other).targetIndex)); // state check
    }
}
