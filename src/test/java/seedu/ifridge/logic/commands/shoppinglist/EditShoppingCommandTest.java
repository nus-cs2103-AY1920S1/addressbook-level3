package seedu.ifridge.logic.commands.shoppinglist;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.DESC_NUTS;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.DESC_ORANGES;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.VALID_NAME_ORANGES;
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
import seedu.ifridge.testutil.EditShoppingItemDescriptorBuilder;
import seedu.ifridge.testutil.ShoppingItemBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and
 * unit tests for EditShoppingCommand.
 */
public class EditShoppingCommandTest {

    private Model model = new ModelManager(getTypicalGroceryList(), new UserPrefs(), getTypicalTemplateList(),
            getTypicalWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList(),
            new UnitDictionary(new HashMap<String, String>()));

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        ShoppingItem editedShoppingItem = new ShoppingItemBuilder().build();
        EditShoppingCommand.EditShoppingItemDescriptor descriptor =
                new EditShoppingItemDescriptorBuilder(editedShoppingItem).build();
        EditShoppingCommand editShoppingCommand = new EditShoppingCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage =
                String.format(EditShoppingCommand.MESSAGE_EDIT_SHOPPING_ITEM_SUCCESS, editedShoppingItem);

        Model expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), model.getTemplateList(),
                model.getWasteArchive(), model.getShoppingList(), model.getBoughtList(), model.getUnitDictionary());
        expectedModel.setShoppingItem(model.getFilteredShoppingList().get(0), editedShoppingItem);
        expectedModel.sortShoppingItems();

        ShoppingCommandTestUtil.assertCommandSuccess(editShoppingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastShoppingItem = Index.fromOneBased(model.getFilteredShoppingList().size());
        ShoppingItem lastShoppingItem = model.getFilteredShoppingList().get(indexLastShoppingItem.getZeroBased());

        ShoppingItemBuilder shoppingItemInList = new ShoppingItemBuilder(lastShoppingItem);
        ShoppingItem editedShoppingItem = shoppingItemInList.withName(VALID_NAME_ORANGES)
                .build();

        EditShoppingCommand.EditShoppingItemDescriptor descriptor =
                new EditShoppingItemDescriptorBuilder().withName(VALID_NAME_ORANGES).build();
        EditShoppingCommand editShoppingCommand = new EditShoppingCommand(indexLastShoppingItem, descriptor);

        String expectedMessage =
                String.format(EditShoppingCommand.MESSAGE_EDIT_SHOPPING_ITEM_SUCCESS, editedShoppingItem);

        Model expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), model.getTemplateList(),
                model.getWasteArchive(), model.getShoppingList(), model.getBoughtList(), model.getUnitDictionary());
        expectedModel.setShoppingItem(lastShoppingItem, editedShoppingItem);
        expectedModel.sortShoppingItems();

        ShoppingCommandTestUtil.assertCommandSuccess(editShoppingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showShoppingItemAtIndex(model, INDEX_FIRST_PERSON);

        ShoppingItem foodInFilteredList = model.getFilteredShoppingList().get(INDEX_FIRST_PERSON.getZeroBased());
        ShoppingItem editedShoppingItem =
                new ShoppingItemBuilder(foodInFilteredList).withName(VALID_NAME_ORANGES).build();
        EditShoppingCommand editShoppingCommand = new EditShoppingCommand(INDEX_FIRST_PERSON,
                new EditShoppingItemDescriptorBuilder().withName(VALID_NAME_ORANGES).build());

        String expectedMessage =
                String.format(EditShoppingCommand.MESSAGE_EDIT_SHOPPING_ITEM_SUCCESS, editedShoppingItem);

        Model expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), model.getTemplateList(),
                model.getWasteArchive(), model.getShoppingList(), model.getBoughtList(), model.getUnitDictionary());
        expectedModel.setShoppingItem(model.getFilteredShoppingList().get(0), editedShoppingItem);
        expectedModel.sortShoppingItems();

        ShoppingCommandTestUtil.assertCommandSuccess(editShoppingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateShoppingItemUnfilteredList_failure() {
        ShoppingItem firstShoppingItem = model.getFilteredShoppingList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditShoppingCommand.EditShoppingItemDescriptor descriptor =
                new EditShoppingItemDescriptorBuilder(firstShoppingItem).build();
        EditShoppingCommand editShoppingCommand = new EditShoppingCommand(INDEX_SECOND_PERSON, descriptor);

        ShoppingCommandTestUtil.assertCommandFailure(editShoppingCommand, model,
                EditShoppingCommand.MESSAGE_DUPLICATE_SHOPPING_ITEM);
    }

    @Test
    public void execute_duplicateShoppingItemFilteredList_failure() {
        showShoppingItemAtIndex(model, INDEX_FIRST_PERSON);

        // edit shoppingItem in filtered list into a duplicate in shopping list
        ShoppingItem foodInList = model.getShoppingList().getShoppingList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditShoppingCommand editShoppingCommand = new EditShoppingCommand(INDEX_FIRST_PERSON,
                new EditShoppingItemDescriptorBuilder(foodInList).build());
        ShoppingCommandTestUtil.assertCommandFailure(editShoppingCommand, model,
                EditShoppingCommand.MESSAGE_DUPLICATE_SHOPPING_ITEM);
    }

    @Test
    public void execute_invalidShoppingItemIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredShoppingList().size() + 1);
        EditShoppingCommand.EditShoppingItemDescriptor descriptor =
                new EditShoppingItemDescriptorBuilder().withName(VALID_NAME_ORANGES).build();
        EditShoppingCommand editShoppingCommand = new EditShoppingCommand(outOfBoundIndex, descriptor);

        ShoppingCommandTestUtil.assertCommandFailure(editShoppingCommand, model,
                Messages.MESSAGE_INVALID_SHOPPING_ITEM_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of shopping list
     */
    @Test
    public void execute_invalidShoppingItemIndexFilteredList_failure() {
        showShoppingItemAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of shopping list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getShoppingList().getShoppingList().size());

        EditShoppingCommand editShoppingCommand = new EditShoppingCommand(outOfBoundIndex,
                new EditShoppingItemDescriptorBuilder().withName(VALID_NAME_ORANGES).build());

        ShoppingCommandTestUtil.assertCommandFailure(editShoppingCommand, model,
                Messages.MESSAGE_INVALID_SHOPPING_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditShoppingCommand standardCommand = new EditShoppingCommand(INDEX_FIRST_PERSON, DESC_NUTS);

        // same values -> returns true
        EditShoppingCommand.EditShoppingItemDescriptor copyDescriptor =
                new EditShoppingCommand.EditShoppingItemDescriptor(DESC_NUTS);
        EditShoppingCommand commandWithSameValues = new EditShoppingCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new MergeShoppingCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditShoppingCommand(INDEX_SECOND_PERSON, DESC_NUTS)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditShoppingCommand(INDEX_FIRST_PERSON, DESC_ORANGES)));
    }

}
