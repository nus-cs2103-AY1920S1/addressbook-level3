package seedu.address.logic.commands.note;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Note;

/**
 * Deletes a note identified using it's displayed index from the student record.
 */
public class NoteDeleteCommand extends NoteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " delete {index}: deletes the note identified by the index number used in the displayed notes list.\n"
            + "Note: {index} must be a positive integer\n"
            + "Example: note delete 3";

    public static final String MESSAGE_DELETE_NOTE_SUCCESS = "Deleted Note: %1$s";

    private final Index targetIndex;

    public NoteDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Note> lastShownList = model.getFilteredNotesList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX);
        }

        Note noteToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteNote(noteToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_NOTE_SUCCESS, noteToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NoteDeleteCommand // instanceof handles nulls
                && targetIndex.equals(((NoteDeleteCommand) other).targetIndex)); // state check
    }
}
