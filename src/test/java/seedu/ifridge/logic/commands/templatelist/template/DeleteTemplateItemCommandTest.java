package seedu.ifridge.logic.commands.templatelist.template;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.TEMPLATE_ITEM_CHEESE;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.assertCommandFailure;
import static seedu.ifridge.model.Model.PREDICATE_SHOW_ALL_TEMPLATES;
import static seedu.ifridge.testutil.TypicalBoughtList.getTypicalBoughtList;
import static seedu.ifridge.testutil.TypicalGroceryItems.getTypicalGroceryList;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.ifridge.testutil.TypicalShoppingList.getTypicalShoppingList;
import static seedu.ifridge.testutil.TypicalTemplateList.getTypicalTemplateList;
import static seedu.ifridge.testutil.TypicalUnitDictionary.getTypicalUnitDictionary;
import static seedu.ifridge.testutil.TypicalWasteArchive.getTypicalWasteArchive;

import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import seedu.ifridge.commons.core.Messages;
import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.model.GroceryList;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.ModelManager;
import seedu.ifridge.model.ShoppingList;
import seedu.ifridge.model.TemplateList;
import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.UserPrefs;
import seedu.ifridge.model.WasteList;
import seedu.ifridge.model.food.TemplateItem;
import seedu.ifridge.model.food.UniqueTemplateItems;
import seedu.ifridge.model.waste.WasteMonth;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteTemplateItemCommandTest {

    private Model model = new ModelManager(getTypicalGroceryList(), new UserPrefs(), getTypicalTemplateList(),
            getTypicalWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList(),
            getTypicalUnitDictionary());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        UniqueTemplateItems templateToEdit = model.getFilteredTemplateList().get(INDEX_FIRST.getZeroBased());
        TemplateItem itemToDelete = templateToEdit.get(INDEX_FIRST.getZeroBased());
        DeleteTemplateItemCommand deleteCommand = new DeleteTemplateItemCommand(INDEX_FIRST, INDEX_FIRST);
        UniqueTemplateItems editedTemplate = new UniqueTemplateItems(templateToEdit.getName());
        editedTemplate.setTemplateItems(templateToEdit);

        String expectedMessage = String.format(DeleteTemplateItemCommand.MESSAGE_SUCCESS, itemToDelete);

        Model expectedModel = new ModelManager(
                new GroceryList(model.getGroceryList()), new UserPrefs(model.getUserPrefs()),
                new TemplateList(model.getTemplateList()), new TreeMap<WasteMonth, WasteList>(model.getWasteArchive()),
                new ShoppingList(model.getShoppingList()), new GroceryList(model.getBoughtList()),
                new UnitDictionary(model.getUnitDictionary()));

        editedTemplate.remove(itemToDelete);

        expectedModel.setTemplate(templateToEdit, editedTemplate);
        expectedModel.updateFilteredTemplateList(PREDICATE_SHOW_ALL_TEMPLATES);
        expectedModel.setShownTemplate(editedTemplate);
        expectedModel.updateFilteredTemplateToBeShown();
        expectedModel.commitTemplateList(templateToEdit, editedTemplate, 0);

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

    @Test
    public void equals() {
        DeleteTemplateItemCommand deleteFirstCommand = new DeleteTemplateItemCommand(INDEX_FIRST,
                INDEX_FIRST);
        DeleteTemplateItemCommand deleteSecondCommand = new DeleteTemplateItemCommand(INDEX_FIRST,
                INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTemplateItemCommand deleteFirstCommandCopy = new DeleteTemplateItemCommand(INDEX_FIRST,
                INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(new AddTemplateItemCommand(INDEX_FIRST, TEMPLATE_ITEM_CHEESE)));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
