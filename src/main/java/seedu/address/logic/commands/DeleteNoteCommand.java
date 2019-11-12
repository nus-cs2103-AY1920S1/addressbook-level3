package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Note;





/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteNoteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the note identified by the index number used in the displayed note list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_NOTE_SUCCESS = "Deleted Note with title: %1$s";

    private final Index targetIndex;
    private final String command;

    public DeleteNoteCommand(Index targetIndex, String commandArgs) {
        this.targetIndex = targetIndex;
        this.command = COMMAND_WORD + " " + commandArgs;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Note> lastShownList = model.getFilteredNoteList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX);
        }
        model.commitNoteBook(command);
        Note noteToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteNote(noteToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_NOTE_SUCCESS, noteToDelete.getTitle()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteNoteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteNoteCommand) other).targetIndex)); // state check
    }
}
