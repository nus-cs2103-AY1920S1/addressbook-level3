package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Content;
import seedu.address.model.note.DateAdded;
import seedu.address.model.note.DateModified;
import seedu.address.model.note.NoteDescription;
import seedu.address.model.note.Note;
import seedu.address.model.note.NumOfAccess;
import seedu.address.model.note.Title;
import seedu.address.model.tag.Tag;



/**
 * Edits the details of an existing person in the address book.
 */
public class EditNoteCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "No duplicated prefixes are allowed"
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "change description ";

    public static final String MESSAGE_EDIT_NOTE_SUCCESS = "Edited Note: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_NOTE = "A note with this title already exists in the note book.";

    private final Index index;
    private final EditNoteDescriptor editNoteDescriptor;
    private final String command;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditNoteCommand(Index index, EditNoteDescriptor editPersonDescriptor, String commandArgs) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editNoteDescriptor = new EditNoteDescriptor(editPersonDescriptor);
        this.command = COMMAND_WORD + " " + index.getOneBased();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Note> lastShownList = model.getFilteredNoteList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX);
        }

        Note noteToEdit = lastShownList.get(index.getZeroBased());
        Note editedNote = createEditedNote(noteToEdit, editNoteDescriptor);

        if (!noteToEdit.isSameNote(editedNote) && model.hasNote(editedNote)) {
            throw new CommandException(MESSAGE_DUPLICATE_NOTE);
        }

        model.commitNote(command);
        model.setNote(noteToEdit, editedNote);
        model.sortNoteBook();
        return CommandResult.builder(String.format(MESSAGE_EDIT_NOTE_SUCCESS, editedNote))
                .build();
    }

    /**
     * Creates and returns a {@code Note} with the details of {@code noteToEdit}
     * edited with {@code editNoteDescriptor}.
     */
    private static Note createEditedNote(Note noteToEdit, EditNoteDescriptor editNoteDescriptor) {
        assert noteToEdit != null;

        Title updatedTitle = editNoteDescriptor.getTitle().orElse(noteToEdit.getTitle());
        NoteDescription updatedDescription = editNoteDescriptor.getDescription().orElse(noteToEdit.getDescription());
        Content updatedContent = editNoteDescriptor.getContent().orElse(noteToEdit.getContent());
        DateModified updatedDateModified = noteToEdit.updateDateModified();
        DateAdded dateAdded = noteToEdit.getDateAdded();
        NumOfAccess numOfAccess = noteToEdit.getNumOfAccess();
        Set<Tag> updatedTags = editNoteDescriptor.getTags().orElse(noteToEdit.getTags());

        return new Note(updatedTitle, updatedDescription, updatedTags, updatedContent,
                updatedDateModified, dateAdded, numOfAccess);
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
        return index.equals(e.index)
                && editNoteDescriptor.equals(e.editNoteDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditNoteDescriptor {
        private Title title;
        private NoteDescription description;
        private Content content;
        private Set<Tag> tags;

        public EditNoteDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditNoteDescriptor(EditNoteCommand.EditNoteDescriptor toCopy) {
            setTitle(toCopy.title);
            setDescription(toCopy.description);
            setContent(toCopy.content);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, description, content, tags);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setDescription(NoteDescription description) {
            this.description = description;
        }

        public Optional<NoteDescription> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setContent(Content content) {
            this.content = content;
        }

        public Optional<Content> getContent() {
            return Optional.ofNullable(content);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
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
                    && getDescription().equals(e.getDescription())
                    && getContent().equals(e.getContent())
                    && getTags().equals(e.getTags());
        }
    }
}
