package dukecooks.logic.commands.diary;

import static dukecooks.logic.parser.CliSyntax.PREFIX_DIARY_NAME;
import static dukecooks.model.Model.PREDICATE_SHOW_ALL_DIARIES;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import dukecooks.commons.core.Event;
import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.commons.util.CollectionUtil;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.EditCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.DiaryName;

/**
 * Edits the details of an existing diary in Duke Cooks.
 */
public class EditDiaryCommand extends EditCommand {

    public static final String VARIANT_WORD = "diary";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the diary identified "
            + "by the index number used in the displayed diary list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DIARY_NAME + "NAME] "
            + "Example: " + COMMAND_WORD + " " + VARIANT_WORD + " 1 "
            + PREFIX_DIARY_NAME + "newDiaryName";

    public static final String MESSAGE_EDIT_DIARY_SUCCESS = "You have edited the diary: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_DIARY = "This diary already exists in the Duke Cooks.";

    private static Event event;
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

        // Navigate to diary tab
        event = Event.getInstance();
        event.set("diary", "all");

        return new CommandResult(String.format(MESSAGE_EDIT_DIARY_SUCCESS, editedDiary));
    }

    /**
     * Creates and returns a {@code Diary} with the details of {@code diaryToEdit}
     * edited with {@code editDiaryDescriptor}.
     */
    private static Diary createEditedDiary(Diary diaryToEdit, EditDiaryDescriptor editDiaryDescriptor) {
        assert diaryToEdit != null;

        DiaryName updatedDiaryName = editDiaryDescriptor.getDiaryName().orElse(diaryToEdit.getDiaryName());
        if (diaryToEdit.getPages().isEmpty()) {
            return new Diary(updatedDiaryName);
        } else {
            return new Diary(updatedDiaryName, diaryToEdit.getPages());
        }
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
        private DiaryName diaryName;

        public EditDiaryDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDiaryDescriptor(EditDiaryDescriptor toCopy) {
            setDiaryName(toCopy.diaryName);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(diaryName);
        }

        public void setDiaryName(DiaryName diaryName) {
            this.diaryName = diaryName;
        }

        public Optional<DiaryName> getDiaryName() {
            return Optional.ofNullable(diaryName);
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

            return getDiaryName().equals(e.getDiaryName());
        }
    }
}
