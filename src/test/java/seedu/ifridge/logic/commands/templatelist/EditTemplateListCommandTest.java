package seedu.ifridge.logic.commands.templatelist;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.DESC_TEMP_BULK_UP;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.DESC_TEMP_SICK;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.VALID_TEMPLATE_NAME_BULK_UP;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.assertCommandFailure;
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
import seedu.ifridge.logic.commands.templatelist.EditTemplateListCommand.EditTemplateListDescriptor;
import seedu.ifridge.model.GroceryList;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.ModelManager;
import seedu.ifridge.model.ShoppingList;
import seedu.ifridge.model.TemplateList;
import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.UserPrefs;
import seedu.ifridge.model.WasteList;
import seedu.ifridge.model.food.UniqueTemplateItems;
import seedu.ifridge.model.waste.WasteMonth;
import seedu.ifridge.testutil.EditTemplateListDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditTemplateListCommandTest {

    private Model model = new ModelManager(getTypicalGroceryList(), new UserPrefs(), getTypicalTemplateList(),
            getTypicalWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList(),
            getTypicalUnitDictionary());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        UniqueTemplateItems templateToEdit = model.getFilteredTemplateList().get(INDEX_FIRST.getZeroBased());
        EditTemplateListDescriptor descriptor = new EditTemplateListDescriptorBuilder()
                .withName(VALID_TEMPLATE_NAME_BULK_UP).withTemplateItems(templateToEdit).build();

        EditTemplateListCommand editCommand = new EditTemplateListCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditTemplateListCommand.MESSAGE_SUCCESS, templateToEdit);

        Model expectedModel = new ModelManager(
                new GroceryList(model.getGroceryList()), new UserPrefs(model.getUserPrefs()),
                new TemplateList(model.getTemplateList()), new TreeMap<WasteMonth, WasteList>(model.getWasteArchive()),
                new ShoppingList(model.getShoppingList()), new GroceryList(model.getBoughtList()),
                new UnitDictionary(model.getUnitDictionary()));

        expectedModel.setTemplate(templateToEdit, descriptor.getTemplate());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTemplateIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTemplateList().size() + 1);
        EditTemplateListDescriptor descriptor = new EditTemplateListDescriptorBuilder()
                .withName(VALID_TEMPLATE_NAME_BULK_UP).build();
        EditTemplateListCommand editCommand = new EditTemplateListCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditTemplateListCommand standardCommand = new EditTemplateListCommand(INDEX_FIRST, DESC_TEMP_BULK_UP);

        // same values -> returns true
        EditTemplateListDescriptor copyDescriptor = new EditTemplateListDescriptor(DESC_TEMP_BULK_UP);
        EditTemplateListCommand commandWithSameValues = new EditTemplateListCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearTemplateListCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditTemplateListCommand(INDEX_SECOND, DESC_TEMP_BULK_UP)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTemplateListCommand(INDEX_SECOND, DESC_TEMP_SICK)));
    }
}
