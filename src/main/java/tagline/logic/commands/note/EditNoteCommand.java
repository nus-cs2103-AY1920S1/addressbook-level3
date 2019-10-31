// @@author shiweing
package tagline.logic.commands.note;

import static java.util.Objects.requireNonNull;
import static tagline.logic.parser.note.NoteCliSyntax.PREFIX_CONTENT;
import static tagline.logic.parser.note.NoteCliSyntax.PREFIX_TITLE;
import static tagline.model.note.NoteModel.PREDICATE_SHOW_ALL_NOTES;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import tagline.commons.core.Messages;
import tagline.commons.util.CollectionUtil;
import tagline.logic.commands.CommandResult;
import tagline.logic.commands.CommandResult.ViewType;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.Model;
import tagline.model.note.Content;
import tagline.model.note.Note;
import tagline.model.note.NoteId;
import tagline.model.note.TimeCreated;
import tagline.model.note.TimeLastEdited;
import tagline.model.note.Title;
import tagline.model.tag.Tag;

/**
 * Edits the details of an existing note in the address book.
 */
public class EditNoteCommand extends NoteCommand {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the note identified "
            + "by the note index number. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: NOTE_ID (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE "
            + "[" + PREFIX_CONTENT + "CONTENT \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TITLE + " New title "
            + PREFIX_CONTENT + " New content ";

    public static final String MESSAGE_EDIT_NOTE_SUCCESS = "Edited Note: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_NOTE = "The edited note is identical to the original.";

    private final NoteId noteId;
    private final EditNoteDescriptor editNoteDescriptor;

    /**
     * @param noteId of the person in the filtered person list to edit
     * @param editNoteDescriptor details to edit the person with
     */
    public EditNoteCommand(NoteId noteId, EditNoteDescriptor editNoteDescriptor) {
        requireNonNull(noteId);
        requireNonNull(editNoteDescriptor);

        this.noteId = noteId;
        this.editNoteDescriptor = editNoteDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check for invalid note id
        Optional<Note> noteFound = model.findNote(noteId);

        if (noteFound.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTE_INDEX);
        }

        Note noteToEdit = noteFound.get();
        Note editedNote = createEditedNote(noteToEdit, editNoteDescriptor);

        assert noteToEdit.getNoteId().equals(editedNote.getNoteId());
        if (noteToEdit.isUniqueNote(editedNote)) {
            throw new CommandException(MESSAGE_DUPLICATE_NOTE);
        }

        model.setNote(noteToEdit, editedNote);
        model.updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
        return new CommandResult(String.format(MESSAGE_EDIT_NOTE_SUCCESS, editedNote), ViewType.NOTE);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditNoteCommand)) {
            return false;
        }

        // state check
        EditNoteCommand e = (EditNoteCommand) other;
        return noteId.equals(e.noteId)
                && editNoteDescriptor.equals(e.editNoteDescriptor);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Note createEditedNote(Note noteToEdit, EditNoteDescriptor editNoteDescriptor) {
        assert noteToEdit != null;

        NoteId noteId = noteToEdit.getNoteId();
        Title updateTitle = editNoteDescriptor.getTitle().orElse(noteToEdit.getTitle());
        Content updatedContent = editNoteDescriptor.getContent().orElse(noteToEdit.getContent());
        TimeCreated timeCreated = noteToEdit.getTimeCreated();
        TimeLastEdited editTime = new TimeLastEdited();
        Set<Tag> updateTags = editNoteDescriptor.getTags().orElse(noteToEdit.getTags());

        return new Note(noteId, updateTitle, updatedContent, timeCreated, editTime, updateTags);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditNoteDescriptor {
        private Title title;
        private Content content;
        private Set<Tag> tags;

        public EditNoteDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditNoteDescriptor(EditNoteDescriptor toCopy) {
            setTitle(toCopy.title);
            setContent(toCopy.content);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, content, tags);
        }


        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Content> getContent() {
            return Optional.ofNullable(content);
        }

        public void setContent(Content content) {
            this.content = content;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditNoteDescriptor)) {
                return false;
            }

            // state check
            EditNoteDescriptor e = (EditNoteDescriptor) other;

            return getTitle().equals(e.getTitle())
                    && getContent().equals(e.getContent())
                    && getTags().equals(e.getTags());
        }
    }
}
