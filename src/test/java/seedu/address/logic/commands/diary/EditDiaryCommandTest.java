package seedu.address.logic.commands.diary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY_DIARY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB_DIARY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertExerciseCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showDiaryAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DIARY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_DIARY;
import static seedu.address.testutil.diary.TypicalDiaries.getTypicalDiaryRecords;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.diary.EditDiaryCommand.EditDiaryDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.diary.DiaryRecords;
import seedu.address.model.diary.components.Diary;
import seedu.address.testutil.diary.DiaryBuilder;
import seedu.address.testutil.diary.EditDiaryDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditDiaryCommand.
 */
public class EditDiaryCommandTest {

    private Model model = new ModelManager(getTypicalDiaryRecords(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Diary editedDiary = new DiaryBuilder().build();
        EditDiaryDescriptor descriptor = new EditDiaryDescriptorBuilder(editedDiary).build();
        EditDiaryCommand editDiaryCommand = new EditDiaryCommand(INDEX_FIRST_DIARY, descriptor);

        String expectedMessage = String.format(EditDiaryCommand.MESSAGE_EDIT_DIARY_SUCCESS, editedDiary);

        Model expectedModel = new ModelManager(new DiaryRecords(model.getDiaryRecords()), new UserPrefs());
        expectedModel.setDiary(model.getFilteredDiaryList().get(0), editedDiary);

        assertCommandSuccess(editDiaryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastDiary = Index.fromOneBased(model.getFilteredDiaryList().size());
        Diary lastDiary = model.getFilteredDiaryList().get(indexLastDiary.getZeroBased());

        DiaryBuilder diaryInList = new DiaryBuilder(lastDiary);
        Diary editedDiary = diaryInList.withName(VALID_NAME_BOB).build();

        EditDiaryDescriptor descriptor = new EditDiaryDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditDiaryCommand editDiaryCommand = new EditDiaryCommand(indexLastDiary, descriptor);

        String expectedMessage = String.format(EditDiaryCommand.MESSAGE_EDIT_DIARY_SUCCESS, editedDiary);

        Model expectedModel = new ModelManager(new DiaryRecords(model.getDiaryRecords()), new UserPrefs());
        expectedModel.setDiary(lastDiary, editedDiary);

        assertCommandSuccess(editDiaryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showDiaryAtIndex(model, INDEX_FIRST_DIARY);

        Diary diaryInFilteredList = model.getFilteredDiaryList().get(INDEX_FIRST_DIARY.getZeroBased());
        Diary editedDiary = new DiaryBuilder(diaryInFilteredList).withName(VALID_NAME_BOB).build();
        EditDiaryCommand editDiaryCommand = new EditDiaryCommand(INDEX_FIRST_DIARY,
                new EditDiaryDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditDiaryCommand.MESSAGE_EDIT_DIARY_SUCCESS, editedDiary);

        Model expectedModel = new ModelManager(new DiaryRecords(model.getDiaryRecords()), new UserPrefs());
        expectedModel.setDiary(model.getFilteredDiaryList().get(0), editedDiary);

        assertCommandSuccess(editDiaryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateDiaryUnfilteredList_failure() {
        Diary firstDiary = model.getFilteredDiaryList().get(INDEX_FIRST_DIARY.getZeroBased());
        EditDiaryDescriptor descriptor = new EditDiaryDescriptorBuilder(firstDiary).build();
        EditDiaryCommand editDiaryCommand = new EditDiaryCommand(INDEX_SECOND_DIARY, descriptor);

        assertExerciseCommandFailure(editDiaryCommand, model, EditDiaryCommand.MESSAGE_DUPLICATE_DIARY);
    }

    @Test
    public void execute_duplicateDiaryFilteredList_failure() {
        showDiaryAtIndex(model, INDEX_FIRST_DIARY);

        // edit diary in filtered list into a duplicate in Duke Cooks
        Diary diaryInList = model.getDiaryRecords().getDiaryList().get(INDEX_SECOND_DIARY.getZeroBased());
        EditDiaryCommand editDiaryCommand = new EditDiaryCommand(INDEX_FIRST_DIARY,
                new EditDiaryDescriptorBuilder(diaryInList).build());

        assertExerciseCommandFailure(editDiaryCommand, model, EditDiaryCommand.MESSAGE_DUPLICATE_DIARY);
    }

    @Test
    public void execute_invalidDiaryIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDiaryList().size() + 1);
        EditDiaryDescriptor descriptor = new EditDiaryDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditDiaryCommand editDiaryCommand = new EditDiaryCommand(outOfBoundIndex, descriptor);

        assertExerciseCommandFailure(editDiaryCommand, model, Messages.MESSAGE_INVALID_DIARY_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of Diaries
     */
    @Test
    public void execute_invalidDiaryIndexFilteredList_failure() {
        showDiaryAtIndex(model, INDEX_FIRST_DIARY);
        Index outOfBoundIndex = INDEX_SECOND_DIARY;
        // ensures that outOfBoundIndex is still in bounds of diary list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getDiaryRecords().getDiaryList().size());

        EditDiaryCommand editDiaryCommand = new EditDiaryCommand(outOfBoundIndex,
                new EditDiaryDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertExerciseCommandFailure(editDiaryCommand, model, Messages.MESSAGE_INVALID_DIARY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditDiaryCommand standardCommand = new EditDiaryCommand(INDEX_FIRST_DIARY, DESC_AMY_DIARY);

        // same values -> returns true
        EditDiaryDescriptor copyDescriptor = new EditDiaryDescriptor(DESC_AMY_DIARY);
        EditDiaryCommand commandWithSameValues = new EditDiaryCommand(INDEX_FIRST_DIARY, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditDiaryCommand(INDEX_SECOND_DIARY, DESC_AMY_DIARY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditDiaryCommand(INDEX_FIRST_DIARY, DESC_BOB_DIARY)));
    }

}
