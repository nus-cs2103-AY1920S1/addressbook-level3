package seedu.ifridge.logic.commands.shoppinglist;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.showShoppingItemAtIndex;
import static seedu.ifridge.testutil.TypicalBoughtList.getTypicalBoughtList;
import static seedu.ifridge.testutil.TypicalGroceryItems.getTypicalGroceryList;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.ifridge.testutil.TypicalShoppingList.getTypicalShoppingList;
import static seedu.ifridge.testutil.TypicalTemplateList.getTypicalTemplateList;
import static seedu.ifridge.testutil.TypicalWasteArchive.getTypicalWasteArchive;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.ifridge.commons.core.Messages;
import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.ModelManager;
import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.UserPrefs;
import seedu.ifridge.model.food.ShoppingItem;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteShoppingCommand}.
 */
public class DeleteShoppingCommandTest {

    private Model model = new ModelManager(getTypicalGroceryList(), new UserPrefs(), getTypicalTemplateList(),
            getTypicalWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList(),
            new UnitDictionary(new HashMap<String, String>()));

    @Test
    public void execute_validIndexUnfilteredList_success() {
        ShoppingItem shoppingItemToDelete = model.getFilteredShoppingList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteShoppingCommand deleteShoppingCommand = new DeleteShoppingCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteShoppingCommand.MESSAGE_DELETE_SHOPPING_ITEM_SUCCESS,
                shoppingItemToDelete);

        ModelManager expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), model.getTemplateList(),
                model.getWasteArchive(), model.getShoppingList(), model.getBoughtList(), model.getUnitDictionary());
        expectedModel.deleteShoppingItem(shoppingItemToDelete);
        expectedModel.sortShoppingItems();

        assertCommandSuccess(deleteShoppingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredShoppingList().size() + 1);
        DeleteShoppingCommand deleteShoppingCommand = new DeleteShoppingCommand(outOfBoundIndex);

        ShoppingCommandTestUtil.assertCommandFailure(deleteShoppingCommand, model,
                Messages.MESSAGE_INVALID_SHOPPING_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showShoppingItemAtIndex(model, INDEX_FIRST_PERSON);

        ShoppingItem shoppingItemToDelete = model.getFilteredShoppingList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteShoppingCommand deleteShoppingCommand = new DeleteShoppingCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteShoppingCommand.MESSAGE_DELETE_SHOPPING_ITEM_SUCCESS,
                shoppingItemToDelete);

        Model expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), model.getTemplateList(),
                model.getWasteArchive(), model.getShoppingList(), model.getBoughtList(), model.getUnitDictionary());
        expectedModel.deleteShoppingItem(shoppingItemToDelete);
        expectedModel.sortShoppingItems();
        showNoShoppingItem(expectedModel);

        assertCommandSuccess(deleteShoppingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showShoppingItemAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getShoppingList().getShoppingList().size());

        DeleteShoppingCommand deleteShoppingCommand = new DeleteShoppingCommand(outOfBoundIndex);

        ShoppingCommandTestUtil.assertCommandFailure(deleteShoppingCommand, model,
                Messages.MESSAGE_INVALID_SHOPPING_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteShoppingCommand deleteFirstCommand = new DeleteShoppingCommand(INDEX_FIRST_PERSON);
        DeleteShoppingCommand deleteSecondCommand = new DeleteShoppingCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteShoppingCommand deleteFirstCommandCopy = new DeleteShoppingCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different shoppingItem -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoShoppingItem(Model model) {
        model.updateFilteredShoppingList(p -> false);

        assertTrue(model.getFilteredShoppingList().isEmpty());
    }
}
