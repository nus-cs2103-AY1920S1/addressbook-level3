package seedu.ifridge.logic.commands.templatelist;

import static seedu.ifridge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ifridge.testutil.TypicalBoughtList.getTypicalBoughtList;
import static seedu.ifridge.testutil.TypicalGroceryItems.getTypicalGroceryList;
import static seedu.ifridge.testutil.TypicalShoppingList.getTypicalShoppingList;
import static seedu.ifridge.testutil.TypicalTemplateList.getTypicalTemplateList;
import static seedu.ifridge.testutil.TypicalUnitDictionary.getTypicalUnitDictionary;
import static seedu.ifridge.testutil.TypicalWasteArchive.getTypicalWasteArchive;

import java.util.HashMap;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import seedu.ifridge.model.GroceryList;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.ModelManager;
import seedu.ifridge.model.ShoppingList;
import seedu.ifridge.model.TemplateList;
import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.UserPrefs;
import seedu.ifridge.model.WasteList;
import seedu.ifridge.model.waste.WasteMonth;


public class ClearTemplateListCommandTest {

    @Test
    public void execute_emptyTemplateList_success() {
        Model model = new ModelManager(new GroceryList(), new UserPrefs(), new TemplateList(),
                new TreeMap<WasteMonth, WasteList>(), new ShoppingList(), new GroceryList(),
                new UnitDictionary(new HashMap<String, String>()));
        Model expectedModel = new ModelManager(new GroceryList(), new UserPrefs(), new TemplateList(),
                new TreeMap<WasteMonth, WasteList>(), new ShoppingList(), new GroceryList(),
                new UnitDictionary(new HashMap<String, String>()));

        assertCommandSuccess(new ClearTemplateListCommand(), model, ClearTemplateListCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyTemplateList_success() {

        Model model = new ModelManager(getTypicalGroceryList(), new UserPrefs(), getTypicalTemplateList(),
                getTypicalWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList(),
                getTypicalUnitDictionary());
        Model expectedModel = new ModelManager(
                new GroceryList(model.getGroceryList()), new UserPrefs(model.getUserPrefs()),
                new TemplateList(model.getTemplateList()), new TreeMap<WasteMonth, WasteList>(model.getWasteArchive()),
                new ShoppingList(model.getShoppingList()), new GroceryList(model.getBoughtList()),
                new UnitDictionary(model.getUnitDictionary()));

        expectedModel.setTemplateList(new TemplateList());

        assertCommandSuccess(new ClearTemplateListCommand(), model, ClearTemplateListCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

}
