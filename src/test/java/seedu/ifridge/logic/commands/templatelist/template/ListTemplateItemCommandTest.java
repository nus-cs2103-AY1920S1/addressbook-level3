package seedu.ifridge.logic.commands.templatelist.template;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX;
import static seedu.ifridge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.assertCommandFailure;
import static seedu.ifridge.model.Model.PREDICATE_SHOW_ALL_TEMPLATES;
import static seedu.ifridge.testutil.TypicalBoughtList.getTypicalBoughtList;
import static seedu.ifridge.testutil.TypicalGroceryItems.getTypicalGroceryList;
import static seedu.ifridge.testutil.TypicalShoppingList.getTypicalShoppingList;
import static seedu.ifridge.testutil.TypicalTemplateList.getTypicalTemplateList;
import static seedu.ifridge.testutil.TypicalUnitDictionary.getTypicalUnitDictionary;
import static seedu.ifridge.testutil.TypicalWasteArchive.getTypicalWasteArchive;

import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
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
import seedu.ifridge.model.food.UniqueTemplateItems;
import seedu.ifridge.model.waste.WasteMonth;



/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListTemplateItemCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalGroceryList(), new UserPrefs(), getTypicalTemplateList(),
                getTypicalWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList(),
                getTypicalUnitDictionary());
        expectedModel = new ModelManager(new GroceryList(model.getGroceryList()), new UserPrefs(model.getUserPrefs()),
                new TemplateList(model.getTemplateList()), new TreeMap<WasteMonth, WasteList>(model.getWasteArchive()),
                new ShoppingList(model.getShoppingList()), new GroceryList(model.getBoughtList()),
                new UnitDictionary(model.getUnitDictionary()));
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        Index indexLastTemplate = Index.fromOneBased(model.getFilteredTemplateList().size());
        UniqueTemplateItems lastTemplate = model.getFilteredTemplateList().get(indexLastTemplate.getZeroBased());
        expectedModel.updateFilteredTemplateList(PREDICATE_SHOW_ALL_TEMPLATES);
        expectedModel.setShownTemplate(lastTemplate);
        expectedModel.updateFilteredTemplateToBeShown();

        String expectedMessage = String.format(ListTemplateItemCommand.MESSAGE_SUCCESS, lastTemplate.getName());

        assertCommandSuccess(new ListTemplateItemCommand(indexLastTemplate), model,
            expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexSpecifiedUnfilteredList_failure() {
        Index outOfBoundTemplateIndex = Index.fromOneBased(model.getFilteredTemplateList().size() + 1);

        assertCommandFailure(new ListTemplateItemCommand(outOfBoundTemplateIndex), model,
                MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
    }
}
