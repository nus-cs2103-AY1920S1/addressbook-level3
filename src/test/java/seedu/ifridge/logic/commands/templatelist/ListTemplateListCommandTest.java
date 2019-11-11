package seedu.ifridge.logic.commands.templatelist;

import static seedu.ifridge.logic.commands.CommandTestUtil.assertCommandSuccess;
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
import seedu.ifridge.model.waste.WasteMonth;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * for {@code ListTemplateListCommand}.
 */
public class ListTemplateListCommandTest {

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
        assertCommandSuccess(new ListTemplateListCommand(),
                model, ListTemplateListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
