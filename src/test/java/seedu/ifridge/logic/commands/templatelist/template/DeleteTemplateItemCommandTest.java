package seedu.ifridge.logic.commands.templatelist.template;

/**import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGroceryItems.getTypicalGroceryList;
import static seedu.address.testutil.TypicalGroceryItems.getTypicalGroceryList;
import static seedu.address.testutil.TypicalShoppingList.getTypicalShoppingList;
import static seedu.address.testutil.TypicalTemplateList.getTypicalTemplateList;
import static seedu.address.testutil.TypicalWasteArchive.getTypicalWasteArchive;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.food.TemplateItem;
import seedu.address.model.food.UniqueTemplateItems;**/

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteTemplateCommand}.
 */
public class DeleteTemplateItemCommandTest {

    /**private Model model = new ModelManager(getTypicalGroceryList(), new UserPrefs(), getTypicalTemplateList(),
                                           getTypicalWasteArchive(), getTypicalShoppingList() );**/

    /**@Test
    public void execute_validIndexUnfilteredList_success() {
        UniqueTemplateItems templateToEdit = model.getFilteredTemplateList().get(INDEX_FIRST.getZeroBased());
        TemplateItem itemToDelete = templateToEdit.get(INDEX_FIRST.getZeroBased());
        DeleteTemplateItemCommand deleteCommand = new DeleteTemplateItemCommand(INDEX_FIRST, INDEX_FIRST);

        String expectedMessage = String.format(DeleteTemplateItemCommand.MESSAGE_SUCCESS, itemToDelete);

        ModelManager expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), model.getTemplateList(),
                                                      model.getWasteArchive(), model.getShoppingList());
        templateToEdit.remove(itemToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTemplateList().size() + 1);

        DeleteTemplateItemCommand withInvalidTempIndex = new DeleteTemplateItemCommand(outOfBoundIndex, INDEX_FIRST);
        assertCommandFailure(withInvalidTempIndex, model, Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);

        DeleteTemplateItemCommand withInvalidItemIndex = new DeleteTemplateItemCommand(INDEX_FIRST, outOfBoundIndex);
        assertCommandFailure(withInvalidItemIndex, model, Messages.MESSAGE_INVALID_TEMPLATE_ITEM_DISPLAYED_INDEX);
    }
    /**
    @Test
    public void execute_validIndexFilteredList_success() {
        showTemplateItemAtIndex(model, INDEX_FIRST);

        TemplateItem itemToDelete = model.getFilteredTemplateItemList().get(INDEX_FIRST.getZeroBased());
        DeleteTemplateItemCommand deleteTemplateCommand = new DeleteTemplateItemCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteTemplateItemCommand.MESSAGE_SUCCESS,
            itemToDelete);

        Model expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs());
        expectedModel.deleteTemplateItem(itemToDelete);
        showNoTemplateItem(expectedModel);

        assertCommandSuccess(deleteTemplateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTemplateItemAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTemplate().getTemplate().size());

        DeleteTemplateItemCommand deleteTemplateCommand = new DeleteTemplateItemCommand(outOfBoundIndex);

        assertCommandFailure(deleteTemplateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    @Test
    public void equals() {
        DeleteTemplateItemCommand deleteFirstCommand = new DeleteTemplateItemCommand(INDEX_FIRST,
                INDEX_FIRST);
        DeleteTemplateItemCommand deleteSecondCommand = new DeleteTemplateItemCommand(INDEX_FIRST,
                INDEX_FIRST);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTemplateItemCommand deleteFirstCommandCopy = new DeleteTemplateItemCommand(INDEX_FIRST,
                INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        //assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    /**
    private void showNoTemplateItem(Model model) {
        model.updateFilteredTemplateItemList(p -> false);

        assertTrue(model.getFilteredTemplateItemList().isEmpty());
    }
    **/
}
