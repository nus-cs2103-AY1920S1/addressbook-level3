package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDiaryAtIndex;
import static seedu.address.testutil.TypicalDiaries.getTypicalDukeCooks;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DIARY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_DIARY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditDiaryCommand.EditDiaryDescriptor;
import seedu.address.model.DukeCooks;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.diary.Diary;
import seedu.address.testutil.DiaryBuilder;
import seedu.address.testutil.EditDiaryDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditDiaryCommand.
 */
public class EditDiaryCommandTest {

    private Model model = new ModelManager(getTypicalDukeCooks(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Diary editedDiary = new DiaryBuilder().build();
        EditDiaryDescriptor descriptor = new EditDiaryDescriptorBuilder(editedDiary).build();
        EditDiaryCommand editDiaryCommand = new EditDiaryCommand(INDEX_FIRST_DIARY, descriptor);

        String expectedMessage = String.format(EditDiaryCommand.MESSAGE_EDIT_DIARY_SUCCESS, editedDiary);

        Model expectedModel = new ModelManager(new DukeCooks(model.getDukeCooks()), new UserPrefs());
        expectedModel.setDiary(model.getFilteredDiaryList().get(0), editedDiary);

        assertCommandSuccess(editDiaryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredDiaryList().size());
        Diary lastDiary = model.getFilteredDiaryList().get(indexLastPerson.getZeroBased());

        DiaryBuilder personInList = new DiaryBuilder(lastDiary);
        Diary editedDiary = personInList.withName(VALID_NAME_BOB).build();

        EditDiaryDescriptor descriptor = new EditDiaryDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditDiaryCommand editDiaryCommand = new EditDiaryCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditDiaryCommand.MESSAGE_EDIT_DIARY_SUCCESS, editedDiary);

        Model expectedModel = new ModelManager(new DukeCooks(model.getDukeCooks()), new UserPrefs());
        expectedModel.setDiary(lastDiary, editedDiary);

        assertCommandSuccess(editDiaryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditDiaryCommand editDiaryCommand = new EditDiaryCommand(INDEX_FIRST_DIARY, new EditDiaryDescriptor());
        Diary editedDiary = model.getFilteredDiaryList().get(INDEX_FIRST_DIARY.getZeroBased());

        String expectedMessage = String.format(EditDiaryCommand.MESSAGE_EDIT_DIARY_SUCCESS, editedDiary);

        Model expectedModel = new ModelManager(new DukeCooks(model.getDukeCooks()), new UserPrefs());

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

        Model expectedModel = new ModelManager(new DukeCooks(model.getDukeCooks()), new UserPrefs());
        expectedModel.setDiary(model.getFilteredDiaryList().get(0), editedDiary);

        assertCommandSuccess(editDiaryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateDiaryUnfilteredList_failure() {
        Diary firstDiary = model.getFilteredDiaryList().get(INDEX_FIRST_DIARY.getZeroBased());
        EditDiaryCommand.EditDiaryDescriptor descriptor = new EditDiaryDescriptorBuilder(firstDiary).build();
        EditDiaryCommand editDiaryCommand = new EditDiaryCommand(INDEX_SECOND_DIARY, descriptor);

        assertCommandFailure(editDiaryCommand, model, EditDiaryCommand.MESSAGE_DUPLICATE_DIARY);
    }

    @Test
    public void execute_duplicateDiaryFilteredList_failure() {
        showDiaryAtIndex(model, INDEX_FIRST_DIARY);

        // edit diary in filtered list into a duplicate in Duke Cooks
        Diary diaryInList = model.getDukeCooks().getDiaryList().get(INDEX_SECOND_DIARY.getZeroBased());
        EditDiaryCommand editDiaryCommand = new EditDiaryCommand(INDEX_FIRST_DIARY,
                new EditDiaryDescriptorBuilder(diaryInList).build());

        assertCommandFailure(editDiaryCommand, model, EditDiaryCommand.MESSAGE_DUPLICATE_DIARY);
    }

    @Test
    public void execute_invalidDiaryIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDiaryList().size() + 1);
        EditDiaryCommand.EditDiaryDescriptor descriptor = new EditDiaryDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        EditDiaryCommand editDiaryCommand = new EditDiaryCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editDiaryCommand, model, Messages.MESSAGE_INVALID_DIARY_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of Duke Cooks
     */
    @Test
    public void execute_invalidDiaryIndexFilteredList_failure() {
        showDiaryAtIndex(model, INDEX_FIRST_DIARY);
        Index outOfBoundIndex = INDEX_SECOND_DIARY;
        // ensures that outOfBoundIndex is still in bounds of Duke Cooks list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getDukeCooks().getDiaryList().size());

        EditDiaryCommand editDiaryCommand = new EditDiaryCommand(outOfBoundIndex,
                new EditDiaryDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editDiaryCommand, model, Messages.MESSAGE_INVALID_DIARY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditDiaryCommand standardCommand = new EditDiaryCommand(INDEX_FIRST_DIARY, DESC_AMY);

        // same values -> returns true
        EditDiaryDescriptor copyDescriptor = new EditDiaryCommand.EditDiaryDescriptor(DESC_AMY);
        EditDiaryCommand commandWithSameValues = new EditDiaryCommand(INDEX_FIRST_DIARY, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditDiaryCommand(INDEX_SECOND_DIARY, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditDiaryCommand(INDEX_FIRST_DIARY, DESC_BOB)));
    }

}
