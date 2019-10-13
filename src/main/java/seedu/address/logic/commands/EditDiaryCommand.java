package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DIARIES;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.Diary;
import seedu.address.model.diary.Name;

/**
 * Edits the details of an existing diary in Duke Cooks.
 */
public class EditDiaryCommand extends Command {

    public static final String COMMAND_WORD = "editDiary";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the diary identified "
            + "by the index number used in the displayed diary list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + "NAME] "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_EDIT_DIARY_SUCCESS = "Edited Diary: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_DIARY = "This diary already exists in the Duke Cooks.";

    private final Index index;
    private final EditDiaryDescriptor editDiaryDescriptor;

    /**
     * @param index of the diary in the filtered diary list to edit
     * @param editDiaryDescriptor details to edit the diary with
     */
    public EditDiaryCommand(Index index, EditDiaryDescriptor editDiaryDescriptor) {
        requireNonNull(index);
        requireNonNull(editDiaryDescriptor);

        this.index = index;
        this.editDiaryDescriptor = new EditDiaryDescriptor(editDiaryDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Diary> lastShownList = model.getFilteredDiaryList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DIARY_DISPLAYED_INDEX);
        }

        Diary diaryToEdit = lastShownList.get(index.getZeroBased());
        Diary editedDiary = createEditedDiary(diaryToEdit, editDiaryDescriptor);

        if (!diaryToEdit.isSameDiary(editedDiary) && model.hasDiary(editedDiary)) {
            throw new CommandException(MESSAGE_DUPLICATE_DIARY);
        }

        model.setDiary(diaryToEdit, editedDiary);
        model.updateFilteredDiaryList(PREDICATE_SHOW_ALL_DIARIES);
        return new CommandResult(String.format(MESSAGE_EDIT_DIARY_SUCCESS, editedDiary));
    }

    /**
     * Creates and returns a {@code Diary} with the details of {@code diaryToEdit}
     * edited with {@code editDiaryDescriptor}.
     */
    private static Diary createEditedDiary(Diary diaryToEdit, EditDiaryDescriptor editDiaryDescriptor) {
        assert diaryToEdit != null;

        Name updatedName = editDiaryDescriptor.getName().orElse(diaryToEdit.getName());

        return new Diary(updatedName);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditDiaryCommand)) {
            return false;
        }

        // state check
        EditDiaryCommand e = (EditDiaryCommand) other;
        return index.equals(e.index)
                && editDiaryDescriptor.equals(e.editDiaryDescriptor);
    }

    /**
     * Stores the details to edit the diary with. Each non-empty field value will replace the
     * corresponding field value of the diary.
     */
    public static class EditDiaryDescriptor {
        private Name name;

        public EditDiaryDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDiaryDescriptor(EditDiaryDescriptor toCopy) {
            setName(toCopy.name);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditDiaryDescriptor)) {
                return false;
            }

            // state check
            EditDiaryDescriptor e = (EditDiaryDescriptor) other;

            return getName().equals(e.getName());
        }
    }
}
