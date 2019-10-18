package seedu.address.logic.commands.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Note;

/**
 * Edits the note details in the note list.
 */
public class NoteEditCommand extends NoteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits an existing note\n"
            + "Parameters:\n"
            + "INDEX: (must be a positive integer) "
            + "note/{Title}"
            + "desc/{Description}\n"
            + "Example: note 1 note/tuesday and wednesday desc/grade papers\n";

    public static final String MESSAGE_EDIT_NOTE_SUCCESS = "Edited Note: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_NOTE = "This note already exists in the notes record.";

    private final Index index;
    private final EditNoteDescriptor editNoteDescriptor;

    /**
     * @param index of the note in the filtered notes list to edit
     * @param editNotesDescriptor details to edit the notes with
     */
    public NoteEditCommand(Index index, EditNoteDescriptor editNotesDescriptor) {
        requireNonNull(index);
        requireNonNull(editNotesDescriptor);
        this.index = index;
        this.editNoteDescriptor = new EditNoteDescriptor(editNotesDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Note> lastShownList = model.getFilteredNotesList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX);
        }

        Note noteToEdit = lastShownList.get(index.getZeroBased());
        Note editedNote = createEditedNote(noteToEdit, editNoteDescriptor);

        if (!noteToEdit.isSameNote(editedNote) && model.hasNote(editedNote)) {
            throw new CommandException(MESSAGE_DUPLICATE_NOTE);
        }

        model.setNote(noteToEdit, editedNote);
        model.updateFilteredNotesList(PREDICATE_SHOW_ALL_NOTES);
        return new CommandResult(String.format(MESSAGE_EDIT_NOTE_SUCCESS, editedNote));
    }

    /**
     * Creates and returns a {@code Note} with the details of {@code noteToEdit}
     * edited with {@code editNoteDescriptor}.
     */
    private static Note createEditedNote(Note noteToEdit, EditNoteDescriptor editNoteDescriptor) {
        assert noteToEdit != null;

        String updatedNote = !editNoteDescriptor.getNote().get().equals("")
                ? editNoteDescriptor.getNote().get()
                : noteToEdit.getNote();
        String updatedDescription = !editNoteDescriptor.getDescription().get().equals("")
                ? editNoteDescriptor.getDescription().get()
                : noteToEdit.getDescription();

        return new Note(updatedNote, updatedDescription);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteEditCommand)) {
            return false;
        }

        // state check
        NoteEditCommand e = (NoteEditCommand) other;
        return index.equals(e.index)
                && editNoteDescriptor.equals(e.editNoteDescriptor);
    }

    /**
     * Stores the details to edit the note with. Each non-empty field value will replace the
     * corresponding field value of the note.
     */
    public static class EditNoteDescriptor {
        private String note;
        private String description;

        public EditNoteDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditNoteDescriptor(NoteEditCommand.EditNoteDescriptor toCopy) {
            setNote(toCopy.note);
            setDescription(toCopy.description);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(note, description);
        }

        public void setNote(String note) {
            this.note = note;
        }

        public Optional<String> getNote() {
            return Optional.ofNullable(note);
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Optional<String> getDescription() {
            return Optional.ofNullable(description);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof NoteEditCommand.EditNoteDescriptor)) {
                return false;
            }

            // state check
            NoteEditCommand.EditNoteDescriptor e = (NoteEditCommand.EditNoteDescriptor) other;

            return getNote().equals(e.getNote())
                    && getDescription().equals(e.getDescription());
        }
    }
}
