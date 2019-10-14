package io.xpire.logic.commands;

import static io.xpire.logic.commands.CommandTestUtil.assertCommandFailure;
import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;
import static io.xpire.logic.commands.CommandTestUtil.showItemAtIndex;
//import static io.xpire.testutil.TypicalIndexes.INDEX_FIFTH_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
//import static io.xpire.testutil.TypicalIndexes.INDEX_FOURTH_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
//import static io.xpire.testutil.TypicalIndexes.INDEX_SEVENTH_ITEM;
//import static io.xpire.testutil.TypicalIndexes.INDEX_SIXTH_ITEM;
//import static io.xpire.testutil.TypicalIndexes.INDEX_THIRD_ITEM;
import static io.xpire.testutil.TypicalItems.getTypicalExpiryDateTracker;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.xpire.commons.core.Messages;
import io.xpire.commons.core.index.Index;
import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;
import io.xpire.model.item.Item;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalExpiryDateTracker(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Item itemToDelete = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ITEM);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, itemToDelete);

        ModelManager expectedModel = new ModelManager(model.getXpire(), new UserPrefs());
        expectedModel.deleteItem(itemToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showItemAtIndex(model, INDEX_FIRST_ITEM);

        Item itemToDelete = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ITEM);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, itemToDelete);

        Model expectedModel = new ModelManager(model.getXpire(), new UserPrefs());
        expectedModel.deleteItem(itemToDelete);
        showNoItem(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showItemAtIndex(model, INDEX_FIRST_ITEM);

        Index outOfBoundIndex = INDEX_SECOND_ITEM;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getXpire().getItemList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    //test to delete tags for item with tags
    @Test
    public void execute_deleteTagsFromItem_success() {
        Item targetItem = model.getFilteredItemList().get(INDEX_SECOND_ITEM.getZeroBased());
        System.out.println(targetItem);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_ITEM);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_ITEM);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_ITEM);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different item -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoItem(Model model) {
        model.updateFilteredItemList(p -> false);

        assertTrue(model.getFilteredItemList().isEmpty());
    }
}
