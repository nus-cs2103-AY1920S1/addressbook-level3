package seedu.address.logic.cap.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.cap.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModule.CS2100;
import static seedu.address.testutil.TypicalModule.CS2103;
import static seedu.address.testutil.TypicalModule.getTypicalCapLog;

import org.junit.jupiter.api.Test;

import seedu.address.model.cap.CapUserPrefs;
import seedu.address.model.cap.Model;
import seedu.address.model.cap.ModelCapManager;
import seedu.address.model.common.Module;


/**
 * Contains integration tests (interaction with the CapModel, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model =
            new ModelCapManager(getTypicalCapLog(), new CapUserPrefs());

    @Test
    public void execute_validModuleUnfilteredList_success() {
        Module moduleToDelete = CS2103;
        DeleteCommand deleteCommand = new DeleteCommand(CS2103.getModuleCode());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete);

        ModelCapManager expectedModel =
                new ModelCapManager(model.getCapLog(), new CapUserPrefs());
        expectedModel.deleteModule(moduleToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(CS2103.getModuleCode());
        DeleteCommand deleteSecondCommand = new DeleteCommand(CS2100.getModuleCode());

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(CS2103.getModuleCode());
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different Module -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
