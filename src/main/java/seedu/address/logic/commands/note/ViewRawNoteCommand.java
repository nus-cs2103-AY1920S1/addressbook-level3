package seedu.address.logic.commands.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.VIEW_RAW;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.NoteCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Note;

/**
 * Finds and lists all notes in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class ViewRawNoteCommand extends Command {

    public static final String COMMAND_WORD = VIEW_RAW;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays a note.\n"
            + "Note is displayed with intra-note tags. Use 'view' to view the cleaned note.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String VIEW_NOTE_SUCCESS = "Viewing raw note: %1$s";

    private final Index targetIndex;

    public ViewRawNoteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Note> lastShownList = model.getFilteredNoteList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX);
        }

        Note note = lastShownList.get(targetIndex.getZeroBased());

        return new NoteCommandResult(model.getFilteredNoteList().isEmpty()
                ? Messages.MESSAGE_NO_MATCHING_NOTE_FOUND
                : String.format(VIEW_NOTE_SUCCESS, note), Optional.of(note));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewRawNoteCommand // instanceof handles nulls
                && targetIndex.equals(((ViewRawNoteCommand) other).targetIndex)); // state check
    }

}
