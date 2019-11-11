package seedu.address.logic.commands.note;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Notes;

/**
 * Deletes a notes identified using it's displayed index from the address book
 */
public class DeleteNotesCommand extends Command {
    public static final String COMMAND_WORD = "deletenote";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the notes identified by the index number used in the displayed notes list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 3";

    public static final String MESSAGE_DELETE_NOTES_SUCCESS = "Deleted Notes: %1$s";

    private final Index targetIndex;

    public DeleteNotesCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Notes> lastShownList = model.getFilteredNotesList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTES_DISPLAYED_INDEX);
        }

        Notes noteToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteNotes(noteToDelete);
        model.commitTutorAid();
        return new CommandResult(String.format(MESSAGE_DELETE_NOTES_SUCCESS, noteToDelete),
                false, false, false, false, false,
                false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteNotesCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteNotesCommand) other).targetIndex)); // state check
    }
}
