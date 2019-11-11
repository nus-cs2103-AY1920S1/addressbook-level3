package seedu.ifridge.logic.commands.grocerylist;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_AMOUNT_NUTS;
import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_NUTS;
import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_NAME_NUTS;
import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_TAG_NUTS;
import static seedu.ifridge.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ifridge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.DESC_NUTS;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.DESC_ORANGES;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.showGroceryItemAtIndex;
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
import seedu.ifridge.logic.commands.grocerylist.EditGroceryCommand.EditGroceryItemDescriptor;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.ModelManager;
import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.UserPrefs;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.testutil.EditGroceryItemDescriptorBuilder;
import seedu.ifridge.testutil.GroceryItemBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditGroceryCommand.
 */
public class EditGroceryCommandTest {

    private Model model = new ModelManager(getTypicalGroceryList(), new UserPrefs(), getTypicalTemplateList(),
            getTypicalWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList(),
            new UnitDictionary(new HashMap<String, String>()));

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        GroceryItem editedGroceryItem = new GroceryItemBuilder().build();
        EditGroceryItemDescriptor descriptor = new EditGroceryItemDescriptorBuilder(editedGroceryItem).build();
        EditGroceryCommand editGroceryCommand = new EditGroceryCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditGroceryCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedGroceryItem);

        Model expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), model.getTemplateList(),
                model.getWasteArchive(), model.getShoppingList(), model.getBoughtList(), model.getUnitDictionary());
        expectedModel.setGroceryItem(model.getFilteredGroceryItemList().get(0), editedGroceryItem);

        assertCommandSuccess(editGroceryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastGroceryItem = Index.fromOneBased(model.getFilteredGroceryItemList().size());
        GroceryItem lastGroceryItem = model.getFilteredGroceryItemList().get(indexLastGroceryItem.getZeroBased());

        GroceryItemBuilder groceryItemInList = new GroceryItemBuilder(lastGroceryItem);
        GroceryItem editedGroceryItem = groceryItemInList.withName(VALID_NAME_NUTS).withTags(VALID_TAG_NUTS).build();

        EditGroceryItemDescriptor descriptor = new EditGroceryItemDescriptorBuilder().withName(VALID_NAME_NUTS)
                .withTags(VALID_TAG_NUTS).build();
        EditGroceryCommand editGroceryCommand = new EditGroceryCommand(indexLastGroceryItem, descriptor);

        String expectedMessage = String.format(EditGroceryCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedGroceryItem);

        Model expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), model.getTemplateList(),
                model.getWasteArchive(), model.getShoppingList(), model.getBoughtList(), model.getUnitDictionary());
        expectedModel.setGroceryItem(lastGroceryItem, editedGroceryItem);

        assertCommandSuccess(editGroceryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_failure() {
        EditGroceryCommand editGroceryCommand = new EditGroceryCommand(INDEX_FIRST_PERSON,
                new EditGroceryItemDescriptor());
        assertCommandFailure(editGroceryCommand, model, EditGroceryCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_filteredList_success() {
        showGroceryItemAtIndex(model, INDEX_FIRST_PERSON);

        GroceryItem groceryItemInFilteredList = model.getFilteredGroceryItemList()
                .get(INDEX_FIRST_PERSON.getZeroBased());
        GroceryItem editedGroceryItem = new GroceryItemBuilder(groceryItemInFilteredList).withName(VALID_NAME_NUTS)
                .withAmount(VALID_AMOUNT_NUTS).withExpiryDate(VALID_EXPIRY_DATE_NUTS).withTags(VALID_TAG_NUTS).build();
        EditGroceryCommand editGroceryCommand = new EditGroceryCommand(INDEX_FIRST_PERSON,
                new EditGroceryItemDescriptorBuilder().withName(VALID_NAME_NUTS).withAmount(VALID_AMOUNT_NUTS)
                        .withExpiryDate(VALID_EXPIRY_DATE_NUTS).withTags(VALID_TAG_NUTS).build());

        String expectedMessage = String.format(EditGroceryCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedGroceryItem);

        Model expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), model.getTemplateList(),
                model.getWasteArchive(), model.getShoppingList(), model.getBoughtList(), model.getUnitDictionary());
        expectedModel.setGroceryItem(model.getFilteredGroceryItemList().get(0), editedGroceryItem);

        assertCommandSuccess(editGroceryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateGroceryItemUnfilteredList_failure() {
        GroceryItem groceryItem = model.getFilteredGroceryItemList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditGroceryItemDescriptor descriptor = new EditGroceryItemDescriptorBuilder(groceryItem).build();
        EditGroceryCommand editGroceryCommand = new EditGroceryCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editGroceryCommand, model, EditGroceryCommand.MESSAGE_DUPLICATE_GROCERY_ITEM);
    }

    @Test
    public void execute_duplicateGroceryItemFilteredList_failure() {
        showGroceryItemAtIndex(model, INDEX_FIRST_PERSON);

        // edit grocery item in filtered list into a duplicate in grocery list
        GroceryItem groceryItemInList = model.getGroceryList().getGroceryList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditGroceryCommand editCommand = new EditGroceryCommand(INDEX_FIRST_PERSON,
                new EditGroceryItemDescriptorBuilder(groceryItemInList).build());

        assertCommandFailure(editCommand, model, EditGroceryCommand.MESSAGE_DUPLICATE_GROCERY_ITEM);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGroceryItemList().size() + 1);
        EditGroceryItemDescriptor descriptor = new EditGroceryItemDescriptorBuilder().withName(VALID_NAME_NUTS).build();
        EditGroceryCommand editGroceryCommand = new EditGroceryCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editGroceryCommand, model, Messages.MESSAGE_INVALID_GROCERY_ITEM_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of grocery list
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showGroceryItemAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getGroceryList().getGroceryList().size());

        EditGroceryCommand editGroceryCommand = new EditGroceryCommand(outOfBoundIndex,
                new EditGroceryItemDescriptorBuilder().withName(VALID_NAME_NUTS).build());

        assertCommandFailure(editGroceryCommand, model, Messages.MESSAGE_INVALID_GROCERY_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditGroceryCommand standardCommand = new EditGroceryCommand(INDEX_FIRST_PERSON, DESC_ORANGES);

        // same values -> returns true
        EditGroceryItemDescriptor copyDescriptor = new EditGroceryItemDescriptor(DESC_ORANGES);
        EditGroceryCommand commandWithSameValues = new EditGroceryCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ListGroceryCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditGroceryCommand(INDEX_SECOND_PERSON, DESC_ORANGES)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditGroceryCommand(INDEX_FIRST_PERSON, DESC_NUTS)));
    }
}
