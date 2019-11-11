package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Content;
import seedu.address.model.note.DateAdded;
import seedu.address.model.note.DateModified;
import seedu.address.model.note.Note;
import seedu.address.model.note.NoteDescription;
import seedu.address.model.note.NumOfAccess;
import seedu.address.model.note.Title;
import seedu.address.model.tag.Tag;


/**
 * Edits the details of an existing person in the address book.
 */
public class OpenNoteCommand extends Command {

    public static final String COMMAND_WORD = "open";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens the note identified "
            + "by the index number used in the displayed note list. "
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_READ_NOTE_SUCCESS = "Note title: %1$s\n";

    private final Index targetIndex;
    private final String command;

    public OpenNoteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.command = COMMAND_WORD + " " + targetIndex.getOneBased();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Note> lastShownList = model.getFilteredNoteList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX);
        }
        Note noteToRead = lastShownList.get(targetIndex.getZeroBased());
        Note updatedNote = createEditedNote(noteToRead);
        //TODO: assert here to ensure updated note is different from any other note in the list
        model.commitNoteBook(command);
        model.setNote(noteToRead, updatedNote);
        model.sortNoteBook();
        Index updatedIndex = model.getNoteIndex(updatedNote);
        return CommandResult.builder("Note opened on the right panel.")
                .setObject(updatedNote)
                .setIndex(updatedIndex)
                .read()
                .build();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OpenNoteCommand // instanceof handles nulls
                && targetIndex.equals(((OpenNoteCommand) other).targetIndex)); // state check
    }

    /**
     * Creates and returns a {@code Note} with the details of {@code noteToEdit}
     */
    private static Note createEditedNote(Note noteToEdit) {
        assert noteToEdit != null;
        Title updatedTitle = noteToEdit.getTitle();
        NoteDescription updatedDescription = noteToEdit.getDescription();
        Content updatedContent = noteToEdit.getContent();
        DateModified updatedDateModified = noteToEdit.getDateModified();
        DateAdded dateAdded = noteToEdit.getDateAdded();
        Set<Tag> updatedTags = noteToEdit.getTags();
        NumOfAccess numOfAccess = noteToEdit.updateNumOfAccess();
        return new Note(updatedTitle, updatedDescription, updatedTags, updatedContent,
                updatedDateModified, dateAdded, numOfAccess);
    }
}


