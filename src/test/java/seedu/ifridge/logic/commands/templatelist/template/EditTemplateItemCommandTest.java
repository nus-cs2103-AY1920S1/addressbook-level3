package seedu.ifridge.logic.commands.templatelist.template;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX;
import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_TEMPLATE_ITEM_DISPLAYED_INDEX;
import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_AMOUNT_CHEESE;
import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_NAME_CHEESE;
import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_NAME_TOMATO_JUICE;
import static seedu.ifridge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.DESC_TEMP_MINCED_MEAT;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.DESC_TEMP_TOMATO_JUICE;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.INVALID_AMOUNT_FOR_QUANTITY;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.INVALID_AMOUNT_FOR_VOLUME;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.INVALID_AMOUNT_FOR_WEIGHT;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.INVALID_NAME_FOR_QUANTITY;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.INVALID_NAME_FOR_VOLUME;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.INVALID_NAME_FOR_WEIGHT;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.VALID_AMOUNT_ORANGES;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.VALID_NAME_ORANGES;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.assertCommandFailure;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.showItemAtIndex;
import static seedu.ifridge.model.Model.PREDICATE_SHOW_ALL_TEMPLATES;
import static seedu.ifridge.model.food.Amount.UNIT_TYPE_QUANTITY;
import static seedu.ifridge.model.food.Amount.UNIT_TYPE_VOLUME;
import static seedu.ifridge.model.food.Amount.UNIT_TYPE_WEIGHT;
import static seedu.ifridge.testutil.TemplateItemBuilder.DEFAULT_AMOUNT;
import static seedu.ifridge.testutil.TemplateItemBuilder.DEFAULT_NAME;
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

import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.logic.commands.templatelist.template.EditTemplateItemCommand.EditTemplateItemDescriptor;
import seedu.ifridge.model.GroceryList;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.ModelManager;
import seedu.ifridge.model.ShoppingList;
import seedu.ifridge.model.TemplateList;
import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.UserPrefs;
import seedu.ifridge.model.WasteList;
import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.TemplateItem;
import seedu.ifridge.model.food.UniqueTemplateItems;
import seedu.ifridge.model.waste.WasteMonth;
import seedu.ifridge.testutil.EditTemplateItemDescriptorBuilder;
import seedu.ifridge.testutil.TemplateItemBuilder;
import seedu.ifridge.testutil.UniqueTemplateItemsBuilder;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditTemplateItemCommandTest {

    private Model model = new ModelManager(getTypicalGroceryList(), new UserPrefs(), getTypicalTemplateList(),
            getTypicalWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList(),
            getTypicalUnitDictionary());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        TemplateItem editedTemplateItem = new TemplateItemBuilder().build();
        EditTemplateItemDescriptor descriptor = new EditTemplateItemDescriptorBuilder(editedTemplateItem).build();
        EditTemplateItemCommand editCommand = new EditTemplateItemCommand(INDEX_FIRST, INDEX_FIRST, descriptor);

        UniqueTemplateItems templateToEdit = model.getFilteredTemplateList().get(0);
        UniqueTemplateItemsBuilder templateItems = new UniqueTemplateItemsBuilder(templateToEdit);
        TemplateItem itemToEdit = templateToEdit.get(0);
        TemplateItemBuilder templateItemInList = new TemplateItemBuilder(itemToEdit);
        TemplateItem editedItem = templateItemInList.withName(DEFAULT_NAME).withAmount(DEFAULT_AMOUNT).build();

        UniqueTemplateItems editedTemplate = templateItems.setTemplateItem(itemToEdit, editedItem);

        String expectedMessage = String.format(EditTemplateItemCommand.MESSAGE_SUCCESS, itemToEdit, editedTemplateItem);

        Model expectedModel = new ModelManager(
                new GroceryList(model.getGroceryList()), new UserPrefs(model.getUserPrefs()),
                new TemplateList(model.getTemplateList()), new TreeMap<WasteMonth, WasteList>(model.getWasteArchive()),
                new ShoppingList(model.getShoppingList()), new GroceryList(model.getBoughtList()),
                new UnitDictionary(model.getUnitDictionary()));
        expectedModel.setTemplate(templateToEdit, editedTemplate);
        expectedModel.updateFilteredTemplateList(PREDICATE_SHOW_ALL_TEMPLATES);
        expectedModel.setShownTemplate(editedTemplate);
        expectedModel.updateFilteredTemplateToBeShown();
        expectedModel.commitTemplateList(templateToEdit, editedTemplate, 0);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTemplate = Index.fromOneBased(model.getFilteredTemplateList().size());
        UniqueTemplateItems lastTemplate = model.getFilteredTemplateList().get(indexLastTemplate.getZeroBased());
        TemplateItem lastTemplateItem = lastTemplate.get(INDEX_FIRST.getZeroBased());

        TemplateItemBuilder templateItemInList = new TemplateItemBuilder(lastTemplateItem);
        UniqueTemplateItemsBuilder templateItems = new UniqueTemplateItemsBuilder(lastTemplate);
        TemplateItem editedTemplateItem = templateItemInList.withName(VALID_NAME_TOMATO_JUICE).build();
        UniqueTemplateItems editedLastTemplate = templateItems.setTemplateItem(lastTemplateItem, editedTemplateItem);

        EditTemplateItemDescriptor descriptor = new EditTemplateItemDescriptorBuilder()
                .withName(VALID_NAME_TOMATO_JUICE).build();
        EditTemplateItemCommand editCommand = new EditTemplateItemCommand(indexLastTemplate, INDEX_FIRST,
                descriptor);

        String expectedMessage = String.format(
                EditTemplateItemCommand.MESSAGE_SUCCESS, lastTemplateItem, editedTemplateItem);

        Model expectedModel = new ModelManager(
                new GroceryList(model.getGroceryList()), new UserPrefs(model.getUserPrefs()),
                new TemplateList(model.getTemplateList()), new TreeMap<WasteMonth, WasteList>(model.getWasteArchive()),
                new ShoppingList(model.getShoppingList()), new GroceryList(model.getBoughtList()),
                new UnitDictionary(model.getUnitDictionary()));

        expectedModel.setTemplate(lastTemplate, editedLastTemplate);
        expectedModel.updateFilteredTemplateList(PREDICATE_SHOW_ALL_TEMPLATES);
        expectedModel.commitTemplateList(lastTemplate, editedLastTemplate, 0);
        expectedModel.setShownTemplate(editedLastTemplate);
        expectedModel.updateFilteredTemplateToBeShown();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditTemplateItemCommand editCommand = new EditTemplateItemCommand(INDEX_FIRST, INDEX_FIRST,
                new EditTemplateItemDescriptor());
        UniqueTemplateItems editedTemplate = model.getFilteredTemplateList().get(INDEX_FIRST.getZeroBased());
        TemplateItem editedTemplateItem = editedTemplate.get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditTemplateItemCommand.MESSAGE_NOT_EDITED, editedTemplateItem);

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_filteredList_success() {
        showItemAtIndex(model, INDEX_FIRST, INDEX_FIRST);

        UniqueTemplateItems filteredTemplate = model.getFilteredTemplateList().get(INDEX_FIRST.getZeroBased());
        UniqueTemplateItems editedTemplate = new UniqueTemplateItemsBuilder(filteredTemplate).build();

        TemplateItem itemInTemplate = filteredTemplate.get(INDEX_FIRST.getZeroBased());
        TemplateItem editedItem = new TemplateItemBuilder(itemInTemplate)
                .withName(VALID_NAME_ORANGES).withAmount(VALID_AMOUNT_ORANGES).build();
        editedTemplate.setTemplateItem(itemInTemplate, editedItem);

        EditTemplateItemCommand editCommand =
                new EditTemplateItemCommand(INDEX_FIRST, INDEX_FIRST, new EditTemplateItemDescriptorBuilder()
                        .withName(VALID_NAME_ORANGES).withAmount(VALID_AMOUNT_ORANGES).build());

        String expectedMessage = String.format(EditTemplateItemCommand.MESSAGE_SUCCESS, itemInTemplate, editedItem);

        Model expectedModel = new ModelManager(
                new GroceryList(model.getGroceryList()), new UserPrefs(model.getUserPrefs()),
                new TemplateList(model.getTemplateList()), new TreeMap<WasteMonth, WasteList>(model.getWasteArchive()),
                new ShoppingList(model.getShoppingList()), new GroceryList(model.getBoughtList()),
                new UnitDictionary(model.getUnitDictionary()));

        expectedModel.setTemplate(model.getFilteredTemplateList().get(0), editedTemplate);
        expectedModel.updateFilteredTemplateList(PREDICATE_SHOW_ALL_TEMPLATES);
        expectedModel.commitTemplateList(filteredTemplate, editedTemplate, 0);
        expectedModel.setShownTemplate(editedTemplate);
        expectedModel.updateFilteredTemplateToBeShown();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTemplateItemUnfilteredList_failure() {
        TemplateItem firstTemplateItem = model.getFilteredTemplateList()
                .get(INDEX_FIRST.getZeroBased()).get(INDEX_FIRST.getZeroBased());
        EditTemplateItemDescriptor descriptor = new EditTemplateItemDescriptorBuilder(firstTemplateItem).build();
        EditTemplateItemCommand editCommand = new EditTemplateItemCommand(INDEX_FIRST, INDEX_SECOND, descriptor);

        assertCommandFailure(editCommand, model, EditTemplateItemCommand.MESSAGE_DUPLICATE_ITEM);
    }

    @Test
    public void execute_invalidNameSpecifiedForUnitUnfilteredList_failure() {
        TemplateItem firstTemplateItem = model.getFilteredTemplateList()
                .get(INDEX_FIRST.getZeroBased()).get(INDEX_FIRST.getZeroBased());
        Name validName = firstTemplateItem.getName();
        Amount validAmount = firstTemplateItem.getAmount();
        String validType = validAmount.getUnitType(validAmount);
        EditTemplateItemDescriptor descriptor;

        assert(validType.equals(UNIT_TYPE_QUANTITY) || validType.equals(UNIT_TYPE_VOLUME)
                || validType.equals(UNIT_TYPE_WEIGHT));

        if (validType.equals(UNIT_TYPE_WEIGHT)) {
            descriptor = new EditTemplateItemDescriptorBuilder().withName(INVALID_NAME_FOR_WEIGHT)
                    .withAmount(validAmount.toString()).build();
        } else if (validType.equals(UNIT_TYPE_VOLUME)) {
            descriptor = new EditTemplateItemDescriptorBuilder().withName(INVALID_NAME_FOR_VOLUME)
                    .withAmount(validAmount.toString()).build();
        } else if (validType.equals(UNIT_TYPE_QUANTITY)) {
            descriptor = new EditTemplateItemDescriptorBuilder().withName(INVALID_NAME_FOR_QUANTITY)
                    .withAmount(validAmount.toString()).build();
        } else {
            descriptor = null;
        }

        EditTemplateItemCommand addCommand = new EditTemplateItemCommand(INDEX_FIRST, INDEX_FIRST, descriptor);

        assertCommandFailure(addCommand, model, EditTemplateItemCommand.MESSAGE_INCORRECT_UNIT);
    }

    @Test
    public void execute_invalidUnitsSpecifiedUnfilteredList_failure() {
        TemplateItem firstTemplateItem = model.getFilteredTemplateList()
                .get(INDEX_FIRST.getZeroBased()).get(INDEX_FIRST.getZeroBased());
        Name validName = firstTemplateItem.getName();
        Amount validAmount = firstTemplateItem.getAmount();
        String validType = validAmount.getUnitType(validAmount);
        EditTemplateItemDescriptor descriptor;

        assert(validType.equals(UNIT_TYPE_QUANTITY) || validType.equals(UNIT_TYPE_VOLUME)
                || validType.equals(UNIT_TYPE_WEIGHT));

        if (validType.equals(UNIT_TYPE_WEIGHT)) {
            descriptor = new EditTemplateItemDescriptorBuilder().withName(validName.toString())
                    .withAmount(INVALID_AMOUNT_FOR_WEIGHT).build();
        } else if (validType.equals(UNIT_TYPE_VOLUME)) {
            descriptor = new EditTemplateItemDescriptorBuilder().withName(validName.toString())
                    .withAmount(INVALID_AMOUNT_FOR_VOLUME).build();
        } else if (validType.equals(UNIT_TYPE_QUANTITY)) {
            descriptor = new EditTemplateItemDescriptorBuilder().withName(validName.toString())
                    .withAmount(INVALID_AMOUNT_FOR_QUANTITY).build();
        } else {
            descriptor = null;
        }

        EditTemplateItemCommand addCommand = new EditTemplateItemCommand(INDEX_FIRST, INDEX_FIRST, descriptor);

        assertCommandFailure(addCommand, model, EditTemplateItemCommand.MESSAGE_INCORRECT_UNIT);
    }

    @Test
    public void execute_invalidTemplateItemIndexUnfilteredList_failure() {
        Index inBoundTemplateIndex = Index.fromOneBased(model.getFilteredTemplateList().size());
        Index outOfBoundTemplateItemIndex = Index.fromOneBased(model.getFilteredTemplateList()
                .get(inBoundTemplateIndex.getZeroBased()).getSize() + 1);

        EditTemplateItemDescriptor descriptor = new EditTemplateItemDescriptorBuilder().withName(VALID_NAME_CHEESE)
                .withAmount(VALID_AMOUNT_CHEESE).build();
        EditTemplateItemCommand editCommand = new EditTemplateItemCommand(inBoundTemplateIndex,
                outOfBoundTemplateItemIndex, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_INVALID_TEMPLATE_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidTemplateIndexUnfilteredList_failure() {
        Index outOfBoundTemplateIndex = Index.fromOneBased(model.getFilteredTemplateList().size() + 1);
        EditTemplateItemDescriptor descriptor = new EditTemplateItemDescriptorBuilder().withName(VALID_NAME_CHEESE)
                .withAmount(VALID_AMOUNT_CHEESE).build();
        EditTemplateItemCommand editCommand =
                new EditTemplateItemCommand(outOfBoundTemplateIndex, INDEX_FIRST, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        // Need to set a new DESC_AMY for CommandTestUtil with a TemplateItemDescriptor
        final EditTemplateItemCommand standardCommand =
                new EditTemplateItemCommand(INDEX_FIRST, INDEX_FIRST, DESC_TEMP_MINCED_MEAT);

        // same values -> returns true
        EditTemplateItemDescriptor copyDescriptor = new EditTemplateItemDescriptor(DESC_TEMP_MINCED_MEAT);
        EditTemplateItemCommand commandWithSameValues =
                new EditTemplateItemCommand(INDEX_FIRST, INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new DeleteTemplateItemCommand(INDEX_FIRST, INDEX_FIRST)));

        // different index -> returns false
        assertFalse(standardCommand.equals(
                new EditTemplateItemCommand(INDEX_SECOND, INDEX_FIRST, DESC_TEMP_MINCED_MEAT)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(
                new EditTemplateItemCommand(INDEX_FIRST, INDEX_FIRST, DESC_TEMP_TOMATO_JUICE)));
    }
}
