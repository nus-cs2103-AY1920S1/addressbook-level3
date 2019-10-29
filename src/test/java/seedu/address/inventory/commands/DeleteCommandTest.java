package seedu.address.inventory.commands;

import org.junit.jupiter.api.Test;
import seedu.address.inventory.logic.commands.DeleteCommand;
import seedu.address.inventory.model.ModelManager;
import seedu.address.inventory.ui.InventoryMessages;
import seedu.address.testutil.TypicalItem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.inventory.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.inventory.commands.CommandTestUtil.assertCommandSuccess;

public class DeleteCommandTest {
    private ModelManager model = new ModelManager(TypicalItem.getTypicalInventoryListForInventoryUse());

    @Test
    void execute_validIndex_successful() {
        DeleteCommand deleteIndexCommand = new DeleteCommand(1);
        String message = String.format(InventoryMessages.MESSAGE_DELETED_ITEM, TypicalItem.getTypicalItems().get(1));
        ModelManager expectedModel = new ModelManager(TypicalItem.getTypicalInventoryListForInventoryUse());
        System.out.println(expectedModel.getInventoryList().get(0));
        expectedModel.deleteItem(1);
        assertCommandSuccess(deleteIndexCommand, model, message, expectedModel);
    }

    @Test
    void execute_outOfBounds_unsuccessful() {
        DeleteCommand deleteCommandOutOfBounds = new DeleteCommand(20);
        assertCommandFailure(deleteCommandOutOfBounds, model, InventoryMessages.NO_SUCH_INDEX_INVENTORY);
    }

    @Test
    public void equals() {
        DeleteCommand delete1Command = new DeleteCommand(1);
        DeleteCommand delete1CommandCopy = new DeleteCommand(1);
        DeleteCommand delete2Command = new DeleteCommand(2);

        // same object -> returns true
        assertTrue(delete1Command.equals(delete1Command));

        assertTrue(delete1Command.equals(delete1CommandCopy));

        // different types -> returns false
        assertFalse(delete1Command.equals(1));

        // null -> returns false
        assertFalse(delete1Command.equals(null));

        // different delete index command -> returns false
        assertFalse(delete1Command.equals(delete2Command));
    }
}
