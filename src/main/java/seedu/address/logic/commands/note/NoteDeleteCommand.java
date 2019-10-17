package seedu.address.logic.commands.note;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Note;
import seedu.address.model.person.Person;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Deletes a note identified using it's displayed index from the student record.
 */
public class NoteDeleteCommand extends NoteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " [index]: the note identified by the index number used in the displayed notes list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: note 1 delete";

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
