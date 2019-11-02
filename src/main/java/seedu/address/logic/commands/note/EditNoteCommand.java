package seedu.address.logic.commands.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.note.Title;

/**
 * Edits the details of an existing lecture note.
 */
public class EditNoteCommand extends Command {
    public static final String COMMAND_WORD = "editnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the lecture note identified "
            + "by the index number used in the displayed lecture note list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_CONTENT + "CONTENT] "
            + "[" + PREFIX_IMAGE + "]\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + PREFIX_TITLE + "Linked lists "
            + PREFIX_CONTENT + "A linked list may be singly or doubly linked.\n"
            + "Using /i will open up a file dialog to select the image";

    public static final String MESSAGE_EDIT_NOTE_SUCCESS = "Edited lecture note: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided";
    public static final String MESSAGE_DUPLICATE_NOTE = "This title already exists";

    private final Index index;
    private final EditNoteDescriptor editNoteDescriptor;

    /**
     * @param index of the lecture note in the filtered note list to edit
     * @param editNoteDescriptor details to edit the note with
     */
    public EditNoteCommand(Index index, EditNoteDescriptor editNoteDescriptor) {
        requireNonNull(index);
        requireNonNull(editNoteDescriptor);

        this.index = index;
        this.editNoteDescriptor = new EditNoteDescriptor(editNoteDescriptor);
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

        editedNote.finalizeImage(model.getAppDataFilePath().getParent());
        model.setNote(noteToEdit, editedNote);
        model.updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
        return new CommandResult(String.format(MESSAGE_EDIT_NOTE_SUCCESS, editedNote));
    }

    /**
     * Creates and returns a {@code Note} with the details of {@code noteToEdit}
     * edited with {@code editNoteDescriptor}.
     */
    private static Note createEditedNote(Note noteToEdit, EditNoteDescriptor editNoteDescriptor) {
        assert noteToEdit != null;

        Title updatedTitle = editNoteDescriptor.getTitle().orElse(noteToEdit.getTitle());
        Content updatedContent = editNoteDescriptor.getContent().orElse(noteToEdit.getContent());
        if (editNoteDescriptor.getImageRemoved()) {
            return new Note(updatedTitle, updatedContent);
        }
        if (editNoteDescriptor.getImageReplaced()) {
            return new Note(updatedTitle, updatedContent, true);
        }
        return new Note(updatedTitle, updatedContent, noteToEdit.getImage());
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
     * Stores the details to edit the lecture note with.
     * A non-empty title or content will replace the corresponding field value of the note;
     * the image is edited if <code>isImageReplaced</code> is true.
     */
    public static class EditNoteDescriptor {
        private Title title;
        private Content content;
        private boolean isImageRemoved = false;
        private boolean isImageReplaced = false;

        public EditNoteDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditNoteDescriptor(EditNoteDescriptor toCopy) {
            setTitle(toCopy.title);
            setContent(toCopy.content);
            setImageRemoved(toCopy.isImageRemoved);
            setImageReplaced(toCopy.isImageReplaced);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, content) || isImageRemoved || isImageReplaced;
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setContent(Content content) {
            this.content = content;
        }

        public Optional<Content> getContent() {
            return Optional.ofNullable(content);
        }

        public void setImageRemoved(boolean isImageRemoved) {
            this.isImageRemoved = isImageRemoved;
        }

        boolean getImageRemoved() {
            return isImageRemoved;
        }

        public void setImageReplaced(boolean isImageReplaced) {
            this.isImageReplaced = isImageReplaced;
        }

        boolean getImageReplaced() {
            return isImageReplaced;
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
                    && isImageRemoved == e.isImageRemoved
                    && isImageReplaced == e.isImageReplaced;
        }
    }
}
