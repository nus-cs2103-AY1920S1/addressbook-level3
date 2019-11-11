package seedu.ifridge.logic.commands.shoppinglist;

import static seedu.ifridge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ifridge.testutil.TypicalBoughtList.getTypicalBoughtList;
import static seedu.ifridge.testutil.TypicalGroceryItems.getTypicalGroceryList;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.ifridge.testutil.TypicalShoppingList.getTypicalShoppingList;
import static seedu.ifridge.testutil.TypicalTemplateList.getTypicalTemplateList;
import static seedu.ifridge.testutil.TypicalWasteArchive.getTypicalWasteArchive;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.ifridge.model.Model;
import seedu.ifridge.model.ModelManager;
import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.UserPrefs;
import seedu.ifridge.model.food.ShoppingItem;

public class RedoShoppingCommandTest {
    private Model model = new ModelManager(getTypicalGroceryList(), new UserPrefs(), getTypicalTemplateList(),
            getTypicalWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList(),
            new UnitDictionary(new HashMap<String, String>()));

    @Test
    public void execute_shoppingListCanRedo_redoSuccessful() {
        ShoppingItem shoppingItemToDelete = model.getFilteredShoppingList().get(INDEX_FIRST_PERSON.getZeroBased());
        RedoShoppingCommand redoShoppingCommand = new RedoShoppingCommand();

        String expectedMessage = RedoShoppingCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), model.getTemplateList(),
                model.getWasteArchive(), model.getShoppingList(), model.getBoughtList(), model.getUnitDictionary());
        expectedModel.deleteShoppingItem(shoppingItemToDelete);
        expectedModel.sortShoppingItems();

        model.deleteShoppingItem(shoppingItemToDelete);
        model.commitShoppingList();
        model.commitBoughtList();
        model.undoShoppingList();
        model.undoBoughtList();

        assertCommandSuccess(redoShoppingCommand, model, expectedMessage, expectedModel);
    }
}
