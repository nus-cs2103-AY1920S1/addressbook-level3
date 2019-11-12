package seedu.address.logic.commands.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.VIEW;

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
public class ViewNoteCommand extends Command {

    public static final String COMMAND_WORD = VIEW;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays a note.\n"
            + "Note is displayed without intra-note tags. Use 'viewraw' to view the raw note.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String VIEW_NOTE_SUCCESS = "Viewing note: %1$s";

    private final Index targetIndex;

    public ViewNoteCommand(Index targetIndex) {
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
        Note cleanedNote = new Note(note.getTitle(), note.getContentCleanedFromTags(), note.getTags());

        return new NoteCommandResult(model.getFilteredNoteList().isEmpty()
                ? Messages.MESSAGE_NO_MATCHING_NOTE_FOUND
                : String.format(VIEW_NOTE_SUCCESS, cleanedNote), Optional.of(cleanedNote));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewNoteCommand // instanceof handles nulls
                && targetIndex.equals(((ViewNoteCommand) other).targetIndex)); // state check
    }

}
