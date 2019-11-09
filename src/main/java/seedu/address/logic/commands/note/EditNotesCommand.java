package seedu.address.logic.commands.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.classid.ClassId;
import seedu.address.model.note.ClassType;
import seedu.address.model.note.Content;
import seedu.address.model.note.Notes;

/**
 * Edits the details of an existing notes in the address book.
 */
public class EditNotesCommand extends Command {
    public static final String COMMAND_WORD = "editnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the notes identified "
            + "by the index number used in the displayed notes list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_CLASSID + "NAME] "
            + "[" + PREFIX_TYPE + "TYPE] "
            + "[" + PREFIX_CONTENT + "CONTENT] "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_EDIT_NOTES_SUCCESS = "Edited Notes: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_NOTES = "This notes already exists in the address book.";

    private final Index index;
    private final EditNotesDescriptor editNotesDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editNotesDescriptor details to edit the person with
     */
    public EditNotesCommand(Index index, EditNotesDescriptor editNotesDescriptor) {
        requireNonNull(index);
        requireNonNull(editNotesDescriptor);

        this.index = index;
        this.editNotesDescriptor = new EditNotesDescriptor(editNotesDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Notes> lastShownList = model.getFilteredNotesList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTES_DISPLAYED_INDEX);
        }

        Notes notesToEdit = lastShownList.get(index.getZeroBased());
        Notes editedNote = createEditedNotes(notesToEdit, editNotesDescriptor);

        if (!notesToEdit.isSameNote(editedNote) && model.hasNotes(editedNote)) {
            throw new CommandException(MESSAGE_DUPLICATE_NOTES);
        }

        model.setNotes(notesToEdit, editedNote);
        model.commitTutorAid();
        model.updateFilteredNotesList(PREDICATE_SHOW_ALL_NOTES);
        return new CommandResult(String.format(MESSAGE_EDIT_NOTES_SUCCESS, editedNote),
                false, false, false, false, false,
                false, true, false);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Notes createEditedNotes(Notes notesToEdit, EditNotesDescriptor editNotesDescriptor) {
        assert notesToEdit != null;

        ClassId updatedCode = editNotesDescriptor.getCode().orElse(notesToEdit.getCode());
        ClassType updateType = editNotesDescriptor.getClassType().orElse(notesToEdit.getType());
        Content updatedContent = editNotesDescriptor.getContent().orElse(notesToEdit.getContent());

        return new Notes(updatedCode, updateType, updatedContent);
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
        EditNotesCommand e = (EditNotesCommand) other;
        return index.equals(e.index)
                && editNotesDescriptor.equals(e.editNotesDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditNotesDescriptor {
        private ClassId code;
        private ClassType type;
        private Content content;

        public EditNotesDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditNotesDescriptor(EditNotesDescriptor toCopy) {
            setModuleCode(toCopy.code);
            setClassType(toCopy.type);
            setContent(toCopy.content);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(code, type, content);
        }

        public void setModuleCode(ClassId code) {
            this.code = code;
        }

        public Optional<ClassId> getCode() {
            return Optional.ofNullable(code);
        }

        public void setClassType(ClassType type) {
            this.type = type;
        }

        public Optional<ClassType> getClassType() {
            return Optional.ofNullable(type);
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
            if (!(other instanceof EditNotesDescriptor)) {
                return false;
            }

            // state check
            EditNotesDescriptor e = (EditNotesDescriptor) other;

            return getCode().equals(e.getCode())
                    && getClassType().equals(e.getClassType())
                    && getContent().equals(e.getContent());
        }
    }
}
