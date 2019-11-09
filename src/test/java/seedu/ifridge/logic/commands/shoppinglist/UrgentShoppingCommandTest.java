package seedu.ifridge.logic.commands.shoppinglist;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
 * {@code UrgentShoppingCommand}.
 */
public class UrgentShoppingCommandTest {

    private Model model = new ModelManager(getTypicalGroceryList(), new UserPrefs(), getTypicalTemplateList(),
            getTypicalWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList(),
            new UnitDictionary(new HashMap<String, String>()));

    @Test
    public void execute_validIndexUnfilteredList_success() {
        ShoppingItem shoppingItemToUrgent = model.getFilteredShoppingList().get(INDEX_FIRST_PERSON.getZeroBased());
        UrgentShoppingCommand urgentShoppingCommand = new UrgentShoppingCommand(INDEX_FIRST_PERSON);

        ModelManager expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), model.getTemplateList(),
                model.getWasteArchive(), model.getShoppingList(), model.getBoughtList(), model.getUnitDictionary());
        expectedModel.urgentShoppingItem(shoppingItemToUrgent);
        expectedModel.sortShoppingItems();

        shoppingItemToUrgent = shoppingItemToUrgent.setUrgent(true);
        String expectedMessage = String.format(UrgentShoppingCommand.MESSAGE_URGENT_SHOPPING_ITEM_SUCCESS,
                shoppingItemToUrgent);
        ShoppingCommandTestUtil.assertCommandSuccess(urgentShoppingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredShoppingList().size() + 1);
        UrgentShoppingCommand urgentShoppingCommand = new UrgentShoppingCommand(outOfBoundIndex);

        ShoppingCommandTestUtil.assertCommandFailure(urgentShoppingCommand, model,
                Messages.MESSAGE_INVALID_SHOPPING_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showShoppingItemAtIndex(model, INDEX_FIRST_PERSON);

        ShoppingItem shoppingItemToUrgent = model.getFilteredShoppingList().get(INDEX_FIRST_PERSON.getZeroBased());
        UrgentShoppingCommand urgentShoppingCommand = new UrgentShoppingCommand(INDEX_FIRST_PERSON);

        Model expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), model.getTemplateList(),
                model.getWasteArchive(), model.getShoppingList(), model.getBoughtList(), model.getUnitDictionary());
        expectedModel.urgentShoppingItem(shoppingItemToUrgent);
        expectedModel.sortShoppingItems();

        shoppingItemToUrgent = shoppingItemToUrgent.setUrgent(true);
        String expectedMessage = String.format(UrgentShoppingCommand.MESSAGE_URGENT_SHOPPING_ITEM_SUCCESS,
                shoppingItemToUrgent);
        ShoppingCommandTestUtil.assertCommandSuccess(urgentShoppingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showShoppingItemAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getShoppingList().getShoppingList().size());

        UrgentShoppingCommand urgentShoppingCommand = new UrgentShoppingCommand(outOfBoundIndex);

        ShoppingCommandTestUtil.assertCommandFailure(urgentShoppingCommand, model,
                Messages.MESSAGE_INVALID_SHOPPING_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UrgentShoppingCommand urgentFirstCommand = new UrgentShoppingCommand(INDEX_FIRST_PERSON);
        UrgentShoppingCommand urgentSecondCommand = new UrgentShoppingCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(urgentFirstCommand.equals(urgentFirstCommand));

        // same values -> returns true
        UrgentShoppingCommand urgentFirstCommandCopy = new UrgentShoppingCommand(INDEX_FIRST_PERSON);
        assertTrue(urgentFirstCommand.equals(urgentFirstCommandCopy));

        // different types -> returns false
        assertFalse(urgentFirstCommand.equals(1));

        // null -> returns false
        assertFalse(urgentFirstCommand.equals(null));

        // different shoppingItem -> returns false
        assertFalse(urgentFirstCommand.equals(urgentSecondCommand));
    }

}
