package seedu.ifridge.storage.shoppinglist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.ifridge.commons.exceptions.IllegalValueException;
import seedu.ifridge.commons.util.JsonUtil;
import seedu.ifridge.model.ShoppingList;
import seedu.ifridge.testutil.TypicalShoppingList;

public class JsonSerializableShoppingListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableShoppingListTest");
    private static final Path TYPICAL_SHOPPING_ITEMS_FILE =
            TEST_DATA_FOLDER.resolve("typicalShoppingItemsShoppingList.json");
    private static final Path INVALID_SHOPPING_ITEM_FILE =
            TEST_DATA_FOLDER.resolve("invalidShoppingItemShoppingList.json");
    private static final Path DUPLICATE_SHOPPING_ITEM_FILE =
            TEST_DATA_FOLDER.resolve("duplicateShoppingItemShoppingList.json");

    @Test
    public void toModelType_typicalShoppingListFile_success() throws Exception {
        JsonSerializableShoppingList dataFromFile = JsonUtil.readJsonFile(TYPICAL_SHOPPING_ITEMS_FILE,
                JsonSerializableShoppingList.class).get();
        ShoppingList shoppingListFromFile = dataFromFile.toModelType();
        ShoppingList typicalShoppingListShoppingList = TypicalShoppingList.getTypicalShoppingList();
        assertEquals(shoppingListFromFile, typicalShoppingListShoppingList);
    }

    @Test
    public void toModelType_invalidShoppingItemFile_throwsIllegalValueException() throws Exception {
        JsonSerializableShoppingList dataFromFile = JsonUtil.readJsonFile(INVALID_SHOPPING_ITEM_FILE,
                JsonSerializableShoppingList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateShoppingList_throwsIllegalValueException() throws Exception {
        JsonSerializableShoppingList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_SHOPPING_ITEM_FILE,
                JsonSerializableShoppingList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile :: toModelType,
                JsonSerializableShoppingList.MESSAGE_DUPLICATE_SHOPPING_ITEMS);
    }

}
