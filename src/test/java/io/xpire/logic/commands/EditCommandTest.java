/*
package io.xpire.logic.commands;

import static io.xpire.logic.commands.CommandTestUtil.DESC_APPLE;
import static io.xpire.logic.commands.CommandTestUtil.DESC_KIWI;
import static io.xpire.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_MILK;
import static io.xpire.logic.commands.CommandTestUtil.VALID_NAME_APPLE;
import static io.xpire.logic.commands.CommandTestUtil.VALID_NAME_KIWI;
import static io.xpire.logic.commands.CommandTestUtil.VALID_NAME_MILK;
import static io.xpire.logic.commands.CommandTestUtil.VALID_QUANTITY_MILK;
import static io.xpire.logic.commands.CommandTestUtil.assertCommandFailure;
import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;
import static io.xpire.logic.commands.CommandTestUtil.showItemAtIndex;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static io.xpire.testutil.TypicalItems.getTypicalExpiryDateTracker;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.xpire.commons.core.Messages;
import io.xpire.commons.core.index.Index;
import io.xpire.logic.commands.EditCommand.EditItemDescriptor;
import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;
import io.xpire.model.Xpire;
import io.xpire.model.item.Item;
import io.xpire.testutil.EditItemDescriptorBuilder;
import io.xpire.testutil.ItemBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */

/*
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalExpiryDateTracker(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Item editedItem = new ItemBuilder().build();
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder(editedItem).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ITEM, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new Xpire(model.getXpire()), new UserPrefs());
        expectedModel.setItem(model.getFilteredItemList().get(0), editedItem);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastItem = Index.fromOneBased(model.getFilteredItemList().size());
        Item lastItem = model.getFilteredItemList().get(indexLastItem.getZeroBased());

        ItemBuilder itemInList = new ItemBuilder(lastItem);
        Item editedItem = itemInList.withName(VALID_NAME_MILK).withExpiryDate(VALID_EXPIRY_DATE_MILK)
                            .withQuantity(VALID_QUANTITY_MILK).build();

        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withName(VALID_NAME_MILK)
                                                                       .withExpiryDate(VALID_EXPIRY_DATE_MILK)
                                                                       .withQuantity(VALID_QUANTITY_MILK).build();

        EditCommand editCommand = new EditCommand(indexLastItem, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new Xpire(model.getXpire()), new UserPrefs());
        expectedModel.setItem(lastItem, editedItem);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ITEM, new EditItemDescriptor());
        Item editedItem = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new Xpire(model.getXpire()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showItemAtIndex(model, INDEX_FIRST_ITEM);

        Item itemInFilteredList = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        Item editedItem = new ItemBuilder(itemInFilteredList).withName(VALID_NAME_KIWI).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ITEM,
                new EditItemDescriptorBuilder().withName(VALID_NAME_KIWI).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new Xpire(model.getXpire()), new UserPrefs());
        expectedModel.setItem(model.getFilteredItemList().get(0), editedItem);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateItemUnfilteredList_failure() {
        Item firstItem = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder(firstItem).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_ITEM, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ITEM);
    }

    @Test
    public void execute_duplicateItemFilteredList_failure() {
        showItemAtIndex(model, INDEX_FIRST_ITEM);

        // edit item in filtered list into a duplicate in address book
        Item itemInList = model.getXpire().getItemList().get(INDEX_SECOND_ITEM.getZeroBased());
        System.out.println(itemInList);
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ITEM,
                new EditItemDescriptorBuilder(itemInList).build());
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ITEM);
    }

    @Test
    public void execute_invalidItemIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withName(VALID_NAME_APPLE).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
/*
    @Test
    public void execute_invalidItemIndexFilteredList_failure() {
        showItemAtIndex(model, INDEX_FIRST_ITEM);
        Index outOfBoundIndex = INDEX_SECOND_ITEM;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getXpire().getItemList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditItemDescriptorBuilder().withName(VALID_NAME_APPLE).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_ITEM, DESC_KIWI);

        // same values -> returns true
        EditItemDescriptor copyDescriptor = new EditItemDescriptor(DESC_KIWI);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_ITEM, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_ITEM, DESC_KIWI)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_ITEM, DESC_APPLE)));
    }

}
*/
