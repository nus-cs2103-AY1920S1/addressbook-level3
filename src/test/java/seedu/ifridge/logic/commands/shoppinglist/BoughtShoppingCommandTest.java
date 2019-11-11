package seedu.ifridge.logic.commands.shoppinglist;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.showShoppingItemAtIndex;
import static seedu.ifridge.model.food.ShoppingItem.isCompletelyBought;
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
import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.ExpiryDate;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.ShoppingItem;

class BoughtShoppingCommandTest {
    private static final String VALID_AMOUNT = "5units";
    private static final String VALID_EXPIRY_DATE = "31/12/2019";

    private Model model = new ModelManager(getTypicalGroceryList(), new UserPrefs(), getTypicalTemplateList(),
            getTypicalWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList(),
            new UnitDictionary(new HashMap<String, String>()));

    @Test
    public void execute_allValidFieldsUnfilteredList_success() {
        ShoppingItem shoppingItemToBought = model.getFilteredShoppingList().get(INDEX_FIRST_PERSON.getZeroBased());
        BoughtShoppingCommand boughtShoppingCommand = new BoughtShoppingCommand(INDEX_FIRST_PERSON,
                new Amount(VALID_AMOUNT), new ExpiryDate(VALID_EXPIRY_DATE));
        GroceryItem boughtItem = shoppingItemToBought.getBoughtItem(new Amount(VALID_AMOUNT),
                new ExpiryDate(VALID_EXPIRY_DATE));
        ShoppingItem boughtShoppingItem = shoppingItemToBought.setBought(true);
        if (isCompletelyBought(shoppingItemToBought, model.getBoughtList().getGroceryList())) {
            boughtShoppingItem = boughtShoppingItem.setUrgent(true);
        }
        String expectedMessage = String.format(BoughtShoppingCommand.MESSAGE_BOUGHT_SHOPPING_ITEM_SUCCESS,
                boughtShoppingItem);

        ModelManager expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), model.getTemplateList(),
                model.getWasteArchive(), model.getShoppingList(), model.getBoughtList(), model.getUnitDictionary());
        expectedModel.addBoughtItem(boughtItem);
        expectedModel.setShoppingItem(shoppingItemToBought, boughtShoppingItem);
        expectedModel.sortShoppingItems();

        assertCommandSuccess(boughtShoppingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredShoppingList().size() + 1);
        BoughtShoppingCommand boughtShoppingCommand = new BoughtShoppingCommand(outOfBoundIndex,
                new Amount(VALID_AMOUNT), new ExpiryDate(VALID_EXPIRY_DATE));

        ShoppingCommandTestUtil.assertCommandFailure(boughtShoppingCommand, model,
                Messages.MESSAGE_INVALID_SHOPPING_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidUnfilteredList_throwsCommandException() {

    }
    @Test
    public void execute_validIndexFilteredList_success() {
        showShoppingItemAtIndex(model, INDEX_FIRST_PERSON);

        ShoppingItem shoppingItemToBought = model.getFilteredShoppingList().get(INDEX_FIRST_PERSON.getZeroBased());
        BoughtShoppingCommand boughtShoppingCommand = new BoughtShoppingCommand(INDEX_FIRST_PERSON,
                new Amount(VALID_AMOUNT), new ExpiryDate(VALID_EXPIRY_DATE));

        ShoppingItem boughtShoppingItem = shoppingItemToBought.setBought(true);
        if (isCompletelyBought(boughtShoppingItem, model.getBoughtList().getGroceryList())) {
            boughtShoppingItem = boughtShoppingItem.setUrgent(false);
        }
        GroceryItem boughtItem = shoppingItemToBought.getBoughtItem(new Amount(VALID_AMOUNT),
                new ExpiryDate(VALID_EXPIRY_DATE));
        String expectedMessage = String.format(BoughtShoppingCommand.MESSAGE_BOUGHT_SHOPPING_ITEM_SUCCESS,
                boughtShoppingItem);

        Model expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), model.getTemplateList(),
                model.getWasteArchive(), model.getShoppingList(), model.getBoughtList(), model.getUnitDictionary());

        expectedModel.addBoughtItem(boughtItem);
        expectedModel.setShoppingItem(shoppingItemToBought, boughtShoppingItem);
        expectedModel.sortShoppingItems();

        assertCommandSuccess(boughtShoppingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showShoppingItemAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getShoppingList().getShoppingList().size());

        BoughtShoppingCommand boughtShoppingCommand = new BoughtShoppingCommand(outOfBoundIndex,
                new Amount(VALID_AMOUNT), new ExpiryDate(VALID_EXPIRY_DATE));

        ShoppingCommandTestUtil.assertCommandFailure(boughtShoppingCommand, model,
                Messages.MESSAGE_INVALID_SHOPPING_ITEM_DISPLAYED_INDEX);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoShoppingItem(Model model) {
        model.updateFilteredShoppingList(p -> false);

        assertTrue(model.getFilteredShoppingList().isEmpty());
    }
}
