package seedu.ifridge.logic.commands.shoppinglist;

import static seedu.ifridge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.showShoppingItemAtIndex;
import static seedu.ifridge.testutil.TypicalBoughtList.getTypicalBoughtList;
import static seedu.ifridge.testutil.TypicalGroceryItems.getTypicalGroceryList;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.ifridge.testutil.TypicalShoppingList.getTypicalShoppingList;
import static seedu.ifridge.testutil.TypicalTemplateList.getTypicalTemplateList;
import static seedu.ifridge.testutil.TypicalWasteArchive.getTypicalWasteArchive;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import seedu.ifridge.model.Model;
import seedu.ifridge.model.ModelManager;
import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListShoppingCommand.
 */
public class ListShoppingCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalGroceryList(), new UserPrefs(), getTypicalTemplateList(),
                getTypicalWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList(),
                new UnitDictionary(new HashMap<String, String>()));
        expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), getTypicalTemplateList(),
                model.getWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList(),
                new UnitDictionary(new HashMap<String, String>()));
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        expectedModel.sortShoppingItems();
        assertCommandSuccess(new ListShoppingCommand(), model,
                ListShoppingCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showShoppingItemAtIndex(model, INDEX_FIRST_PERSON);
        expectedModel.sortShoppingItems();
        assertCommandSuccess(new ListShoppingCommand(), model,
                ListShoppingCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
