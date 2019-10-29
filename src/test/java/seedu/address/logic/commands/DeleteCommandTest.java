package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECORD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RECORD;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Calendar;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.bio.UserList;
import seedu.address.model.record.UniqueRecordList;
import sugarmummy.recmfood.model.UniqueFoodList;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for {@code
 * DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UserList(),
        new UniqueFoodList(), new UniqueRecordList(), new Calendar());

    //    @Test
    //    public void execute_validIndexUnfilteredList_success() {
    //        Record recordToDelete = sugarmummy.recmfood.model.getRecordList().get(INDEX_FIRST_RECORD.getZeroBased());
    //        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_RECORD);
    //
    //        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_RECORD_SUCCESS, recordToDelete);
    //
    //        ModelManager expectedModel = new ModelManager(sugarmummy.recmfood.model.getAddressBook(), new UserPrefs(),
    //        new UserList(),
    //                new UniqueFoodList(), new UniqueRecordList(), new Calendar());
    //
    //        expectedModel.deleteRecord(recordToDelete);
    //
    //        assertCommandSuccess(deleteCommand, sugarmummy.recmfood.model, expectedMessage, expectedModel);
    //    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilterRecordList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }

    //    @Test
    //    public void execute_validIndexFilteredList_success() {
    ////        showRecordAtIndex(sugarmummy.recmfood.model, INDEX_FIRST_RECORD);
    //
    //        Record recordToDelete = sugarmummy.recmfood.model.getFilterRecordList().get(INDEX_FIRST_RECORD.
    //        getZeroBased());
    //        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_RECORD);
    //
    //        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_RECORD_SUCCESS, recordToDelete);
    //
    //        Model expectedModel = new ModelManager(sugarmummy.recmfood.model.getAddressBook(), new UserPrefs(),
    //                new UserList(), new UniqueFoodList(),
    //            new UniqueRecordList(), new Calendar());
    //
    //        expectedModel.deleteRecord(recordToDelete);
    ////        showNoRecord(expectedModel);
    //
    //        assertCommandSuccess(deleteCommand, sugarmummy.recmfood.model, expectedMessage, expectedModel);
    //    }

    //    @Test
    //    public void execute_invalidIndexFilteredList_throwsCommandException() {
    ////        showRecordAtIndex(sugarmummy.recmfood.model, INDEX_FIRST_PERSON);
    //
    //        Index outOfBoundIndex = INDEX_SECOND_RECORD;
    //        // ensures that outOfBoundIndex is still in bounds of address book list
    //        assertTrue(outOfBoundIndex.getZeroBased() < sugarmummy.recmfood.model.getFilterRecordList().size());
    //
    //        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
    //
    //        assertCommandFailure(deleteCommand, sugarmummy.recmfood.model,
    //        Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    //    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_RECORD);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_RECORD);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_RECORD);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different record -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code sugarmummy.recmfood.model}'s filtered list to show no one.
     */
    private void showNoRecord(Model model) {
        model.updateFilteredRecordList(p -> false);

        assertTrue(model.getFilterRecordList().isEmpty());
    }
}
