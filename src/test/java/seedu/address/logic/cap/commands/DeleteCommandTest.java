package seedu.address.logic.cap.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.cap.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MODULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MODULE;
import static seedu.address.testutil.TypicalModule.getTypicalCapLog;

import org.junit.jupiter.api.Test;

import seedu.address.model.cap.Model;
import seedu.address.model.cap.ModelCapManager;
import seedu.address.model.cap.CapUserPrefs;
import seedu.address.model.common.Module;


/**
 * Contains integration tests (interaction with the CapModel, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model =
            new ModelCapManager(getTypicalCapLog(), new CapUserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Module moduleToDelete = model.getFilteredModuleList().get(INDEX_FIRST_MODULE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_MODULE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete);

        ModelCapManager expectedModel =
                new ModelCapManager(model.getCapLog(), new CapUserPrefs());
        expectedModel.deleteModule(moduleToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    //    @Test
    //    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
    //        Index outOfBoundIndex = Index.fromOneBased(CapModel.getFilteredModuleList().size() + 1);
    //        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
    //
    //        assertCommandFailure(deleteCommand, CapModel, Messages.MESSAGE_INVALID_module_DISPLAYED_INDEX);
    //    }
    //
    //    @Test
    //    public void execute_validIndexFilteredList_success() {
    //        showmoduleAtIndex(CapModel, INDEX_FIRST_module);
    //
    //        Module ModuleToDelete = CapModel.getFilteredModuleList().get(INDEX_FIRST_module.getZeroBased());
    //        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_module);
    //
    //        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_module_SUCCESS, ModuleToDelete);
    //
    //        CapModel expectedModel
    //                = new CapModelManager(CapModel.getCapAddressBook(), new CapUserPrefs());
    //        expectedModel.deleteModule(ModuleToDelete);
    //        showNoModule(expectedModel);
    //
    //        assertCommandSuccess(deleteCommand, CapModel, expectedMessage, expectedModel);
    //    }
    //
    //    @Test
    //    public void execute_invalidIndexFilteredList_throwsCommandException() {
    //        showmoduleAtIndex(CapModel, INDEX_FIRST_module);
    //
    //        Index outOfBoundIndex = INDEX_SECOND_module;
    //        // ensures that outOfBoundIndex is still in bounds of address book list
    //        assertTrue(
    //            outOfBoundIndex.getZeroBased() < CapModel.getCapAddressBook().getmoduleList().size());
    //
    //        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
    //
    //        assertCommandFailure(deleteCommand, CapModel, Messages.MESSAGE_INVALID_module_DISPLAYED_INDEX);
    //    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_MODULE);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_MODULE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_MODULE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different Module -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code CapModel}'s filtered list to show no one.
     */
    private void showNoModule(Model capModel) {
        capModel.updateFilteredModuleList(p -> false);

        assertTrue(capModel.getFilteredModuleList().isEmpty());
    }
}
