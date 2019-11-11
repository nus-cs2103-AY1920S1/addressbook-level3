package seedu.ifridge.logic.commands.templatelist.template;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX;
import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_AMOUNT_CHEESE;
import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_NAME_CHEESE;
import static seedu.ifridge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.INVALID_AMOUNT_FOR_QUANTITY;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.INVALID_AMOUNT_FOR_VOLUME;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.INVALID_AMOUNT_FOR_WEIGHT;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.TEMPLATE_ITEM_CHEESE;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.TEMPLATE_ITEM_RICE;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.VALID_AMOUNT_TOMATO_JUICE;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.VALID_NAME_TOMATO_JUICE;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.assertCommandFailure;
import static seedu.ifridge.model.Model.PREDICATE_SHOW_ALL_TEMPLATES;
import static seedu.ifridge.model.food.Amount.UNIT_TYPE_QUANTITY;
import static seedu.ifridge.model.food.Amount.UNIT_TYPE_VOLUME;
import static seedu.ifridge.model.food.Amount.UNIT_TYPE_WEIGHT;
import static seedu.ifridge.testutil.Assert.assertThrows;
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
import seedu.ifridge.model.GroceryList;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.ModelManager;
import seedu.ifridge.model.ShoppingList;
import seedu.ifridge.model.TemplateList;
import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.UserPrefs;
import seedu.ifridge.model.WasteList;
import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.TemplateItem;
import seedu.ifridge.model.food.UniqueTemplateItems;
import seedu.ifridge.model.waste.WasteMonth;
import seedu.ifridge.testutil.TemplateItemBuilder;
import seedu.ifridge.testutil.UniqueTemplateItemsBuilder;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class AddTemplateItemCommandTest {
    private Model model = new ModelManager(getTypicalGroceryList(), new UserPrefs(), getTypicalTemplateList(),
            getTypicalWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList(),
            getTypicalUnitDictionary());

    @Test
    public void constructor_nullTemplateItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTemplateItemCommand(null, null));
    }

    @Test
    public void execute_allFieldsSpecified_addSuccessful() throws Exception {
        TemplateItem validTemplateItem = new TemplateItemBuilder()
                .withName(VALID_NAME_TOMATO_JUICE).withAmount(VALID_AMOUNT_TOMATO_JUICE).build();
        AddTemplateItemCommand addCommand = new AddTemplateItemCommand(INDEX_FIRST, validTemplateItem);

        UniqueTemplateItems templateToEdit = model.getFilteredTemplateList().get(0);
        UniqueTemplateItemsBuilder templateItems = new UniqueTemplateItemsBuilder(templateToEdit);
        UniqueTemplateItems editedTemplate = templateItems.withTemplateItem(validTemplateItem).build();

        String expectedMessage = String.format(AddTemplateItemCommand.MESSAGE_SUCCESS, validTemplateItem);

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

        assertCommandSuccess(addCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateFoodUnfilteredList_addFailure() {
        TemplateItem firstTemplateItem = model.getFilteredTemplateList()
                .get(INDEX_FIRST.getZeroBased()).get(INDEX_FIRST.getZeroBased());

        AddTemplateItemCommand addCommand = new AddTemplateItemCommand(INDEX_FIRST, firstTemplateItem);

        assertCommandFailure(addCommand, model, AddTemplateItemCommand.MESSAGE_DUPLICATE_FOOD);
    }

    @Test
    public void execute_invalidUnitsSpecifiedUnfilteredList_failure() {
        TemplateItem firstTemplateItem = model.getFilteredTemplateList()
                .get(INDEX_SECOND.getZeroBased()).get(INDEX_FIRST.getZeroBased());
        Amount validAmount = firstTemplateItem.getAmount();
        String validType = validAmount.getUnitType(validAmount);
        TemplateItem toAdd;

        assert(validType.equals(UNIT_TYPE_QUANTITY) || validType.equals(UNIT_TYPE_VOLUME)
            || validType.equals(UNIT_TYPE_WEIGHT));

        if (validType.equals(UNIT_TYPE_WEIGHT)) {
            toAdd = new TemplateItemBuilder().withName(firstTemplateItem.getName().toString())
                    .withAmount(INVALID_AMOUNT_FOR_WEIGHT).build();
        } else if (validType.equals(UNIT_TYPE_VOLUME)) {
            toAdd = new TemplateItemBuilder().withName(firstTemplateItem.getName().toString())
                    .withAmount(INVALID_AMOUNT_FOR_VOLUME).build();
        } else if (validType.equals(UNIT_TYPE_QUANTITY)) {
            toAdd = new TemplateItemBuilder().withName(firstTemplateItem.getName().toString())
                    .withAmount(INVALID_AMOUNT_FOR_QUANTITY).build();
        } else {
            toAdd = null;
        }

        AddTemplateItemCommand addCommand = new AddTemplateItemCommand(INDEX_FIRST, toAdd);

        assertCommandFailure(addCommand, model, AddTemplateItemCommand.MESSAGE_INCORRECT_UNIT);
    }

    @Test
    public void execute_invalidTemplateIndexUnfilteredList_failure() {
        Index outOfBoundTemplateIndex = Index.fromOneBased(model.getFilteredTemplateList().size() + 1);
        TemplateItem addedItem = new TemplateItemBuilder().withName(VALID_NAME_CHEESE)
                .withAmount(VALID_AMOUNT_CHEESE).build();
        AddTemplateItemCommand addCommand =
                new AddTemplateItemCommand(outOfBoundTemplateIndex, addedItem);

        assertCommandFailure(addCommand, model, MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        // Need to set a new DESC_AMY for CommandTestUtil with a TemplateItemDescriptor
        final TemplateItem standardItem = TEMPLATE_ITEM_CHEESE;
        final AddTemplateItemCommand standardCommand = new AddTemplateItemCommand(INDEX_FIRST, standardItem);

        // same values -> returns true
        AddTemplateItemCommand commandWithSameValues =
                new AddTemplateItemCommand(INDEX_FIRST, standardItem);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new DeleteTemplateItemCommand(INDEX_FIRST, INDEX_FIRST)));

        // different index -> returns false
        assertFalse(standardCommand.equals(
                new AddTemplateItemCommand(INDEX_SECOND, standardItem)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(
                new AddTemplateItemCommand(INDEX_FIRST, TEMPLATE_ITEM_RICE)));
    }
}
