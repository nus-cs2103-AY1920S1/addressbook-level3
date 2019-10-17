package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Note;


/**
 * Edits the details of an existing person in the address book.
 */
public class ReadNoteCommand extends Command {

    public static final String COMMAND_WORD = "read";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reads the content of the note identified "
            + "by the index number used in the displayed note list. "
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_READ_NOTE_SUCCESS = "Note title: %1$s\n";

    private final Index targetIndex;

    public ReadNoteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Note> lastShownList = model.getFilteredNoteList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX);
        }

        Note noteToRead = lastShownList.get(targetIndex.getZeroBased());
        return new CommandResult(String.format(MESSAGE_READ_NOTE_SUCCESS, noteToRead.getTitle())
                + "//" + noteToRead.getContent() + "//" + targetIndex.getOneBased(), false, false ,
                false, null, true);
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadNoteCommand // instanceof handles nulls
                && targetIndex.equals(((ReadNoteCommand) other).targetIndex)); // state check
    }
}
