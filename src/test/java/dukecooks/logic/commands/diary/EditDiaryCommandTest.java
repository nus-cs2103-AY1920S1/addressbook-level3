package dukecooks.logic.commands.diary;

import static dukecooks.testutil.diary.TypicalDiaries.getTypicalDiaryRecords;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.diary.DiaryRecords;
import dukecooks.model.diary.components.Diary;
import dukecooks.testutil.TypicalIndexes;
import dukecooks.testutil.diary.DiaryBuilder;
import dukecooks.testutil.diary.EditDiaryDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditDiaryCommand.
 */
public class EditDiaryCommandTest {

    private Model model = new ModelManager(getTypicalDiaryRecords(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Diary editedDiary = new DiaryBuilder().build();
        EditDiaryCommand.EditDiaryDescriptor descriptor = new EditDiaryDescriptorBuilder(editedDiary).build();
        EditDiaryCommand editDiaryCommand = new EditDiaryCommand(TypicalIndexes.INDEX_FIRST_DIARY, descriptor);

        String expectedMessage = String.format(EditDiaryCommand.MESSAGE_EDIT_DIARY_SUCCESS, editedDiary);

        Model expectedModel = new ModelManager(new DiaryRecords(model.getDiaryRecords()), new UserPrefs());
        expectedModel.setDiary(model.getFilteredDiaryList().get(0), editedDiary);

        CommandTestUtil.assertCommandSuccess(editDiaryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastDiary = Index.fromOneBased(model.getFilteredDiaryList().size());
        Diary lastDiary = model.getFilteredDiaryList().get(indexLastDiary.getZeroBased());

        DiaryBuilder diaryInList = new DiaryBuilder(lastDiary);
        Diary editedDiary = diaryInList.withName(CommandTestUtil.VALID_NAME_BOB).build();

        EditDiaryCommand.EditDiaryDescriptor descriptor = new EditDiaryDescriptorBuilder().withName(CommandTestUtil
                .VALID_NAME_BOB).build();
        EditDiaryCommand editDiaryCommand = new EditDiaryCommand(indexLastDiary, descriptor);

        String expectedMessage = String.format(EditDiaryCommand.MESSAGE_EDIT_DIARY_SUCCESS, editedDiary);

        Model expectedModel = new ModelManager(new DiaryRecords(model.getDiaryRecords()), new UserPrefs());
        expectedModel.setDiary(lastDiary, editedDiary);

        CommandTestUtil.assertCommandSuccess(editDiaryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        CommandTestUtil.showDiaryAtIndex(model, TypicalIndexes.INDEX_FIRST_DIARY);

        Diary diaryInFilteredList = model.getFilteredDiaryList().get(TypicalIndexes.INDEX_FIRST_DIARY.getZeroBased());
        Diary editedDiary = new DiaryBuilder(diaryInFilteredList).withName(CommandTestUtil.VALID_NAME_BOB).build();
        EditDiaryCommand editDiaryCommand = new EditDiaryCommand(TypicalIndexes.INDEX_FIRST_DIARY,
                new EditDiaryDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditDiaryCommand.MESSAGE_EDIT_DIARY_SUCCESS, editedDiary);

        Model expectedModel = new ModelManager(new DiaryRecords(model.getDiaryRecords()), new UserPrefs());
        expectedModel.setDiary(model.getFilteredDiaryList().get(0), editedDiary);

        CommandTestUtil.assertCommandSuccess(editDiaryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateDiaryUnfilteredList_failure() {
        Diary firstDiary = model.getFilteredDiaryList().get(TypicalIndexes.INDEX_FIRST_DIARY.getZeroBased());
        EditDiaryCommand.EditDiaryDescriptor descriptor = new EditDiaryDescriptorBuilder(firstDiary).build();
        EditDiaryCommand editDiaryCommand = new EditDiaryCommand(TypicalIndexes.INDEX_SECOND_DIARY, descriptor);

        CommandTestUtil.assertDiaryCommandFailure(editDiaryCommand, model, EditDiaryCommand.MESSAGE_DUPLICATE_DIARY);
    }

    @Test
    public void execute_duplicateDiaryFilteredList_failure() {
        CommandTestUtil.showDiaryAtIndex(model, TypicalIndexes.INDEX_FIRST_DIARY);

        // edit diary in filtered list into a duplicate in Duke Cooks
        Diary diaryInList = model.getDiaryRecords().getDiaryList().get(TypicalIndexes.INDEX_SECOND_DIARY
                .getZeroBased());
        EditDiaryCommand editDiaryCommand = new EditDiaryCommand(TypicalIndexes.INDEX_FIRST_DIARY,
                new EditDiaryDescriptorBuilder(diaryInList).build());

        CommandTestUtil.assertDiaryCommandFailure(editDiaryCommand, model, EditDiaryCommand.MESSAGE_DUPLICATE_DIARY);
    }

    @Test
    public void execute_invalidDiaryIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDiaryList().size() + 1);
        EditDiaryCommand.EditDiaryDescriptor descriptor = new EditDiaryDescriptorBuilder().withName(CommandTestUtil
                .VALID_NAME_BOB).build();
        EditDiaryCommand editDiaryCommand = new EditDiaryCommand(outOfBoundIndex, descriptor);

        CommandTestUtil.assertDiaryCommandFailure(editDiaryCommand, model,
                Messages.MESSAGE_INVALID_DIARY_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of Diaries
     */
    @Test
    public void execute_invalidDiaryIndexFilteredList_failure() {
        CommandTestUtil.showDiaryAtIndex(model, TypicalIndexes.INDEX_FIRST_DIARY);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_DIARY;
        // ensures that outOfBoundIndex is still in bounds of diary list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getDiaryRecords().getDiaryList().size());

        EditDiaryCommand editDiaryCommand = new EditDiaryCommand(outOfBoundIndex,
                new EditDiaryDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build());

        CommandTestUtil.assertDiaryCommandFailure(editDiaryCommand, model,
                Messages.MESSAGE_INVALID_DIARY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditDiaryCommand standardCommand = new EditDiaryCommand(TypicalIndexes.INDEX_FIRST_DIARY,
                CommandTestUtil.DESC_AMY_DIARY);

        // same values -> returns true
        EditDiaryCommand.EditDiaryDescriptor copyDescriptor = new EditDiaryCommand
                .EditDiaryDescriptor(CommandTestUtil.DESC_AMY_DIARY);
        EditDiaryCommand commandWithSameValues = new EditDiaryCommand(TypicalIndexes.INDEX_FIRST_DIARY,
                copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditDiaryCommand(TypicalIndexes.INDEX_SECOND_DIARY,
                CommandTestUtil.DESC_AMY_DIARY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditDiaryCommand(TypicalIndexes.INDEX_FIRST_DIARY,
                CommandTestUtil.DESC_BOB_DIARY)));
    }

}
