package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Content;
import seedu.address.model.person.Note;
import seedu.address.model.person.Title;

/**
 * Edits the details of an existing lecture note.
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "editnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the lecture note identified "
            + "by the index number used in the displayed lecture note list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_CONTENT + "CONTENT] "
            + "Example: " + COMMAND_WORD + " 2 "
            + PREFIX_TITLE + "Linked lists "
            + PREFIX_CONTENT + "A linked list may be singly or doubly linked.";

    public static final String MESSAGE_EDIT_NOTE_SUCCESS = "Edited lecture note: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_NOTE = "This title already exists.";

    private final Index index;
    private final EditNoteDescriptor editNoteDescriptor;

    /**
     * @param index of the lecture note in the filtered note list to edit
     * @param editNoteDescriptor details to edit the note with
     */
    public EditCommand(Index index, EditNoteDescriptor editNoteDescriptor) {
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
        return new Note(updatedTitle, updatedContent);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editNoteDescriptor.equals(e.editNoteDescriptor);
    }

    /**
     * Stores the details to edit the lecture note with. Each non-empty field value will replace the
     * corresponding field value of the note.
     */
    public static class EditNoteDescriptor {
        private Title title;
        private Content content;

        public EditNoteDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditNoteDescriptor(EditNoteDescriptor toCopy) {
            setTitle(toCopy.title);
            setContent(toCopy.content);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, content);
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

            return getTitle().equals(e.getTitle()) && getContent().equals(e.getContent());
        }
    }
}
