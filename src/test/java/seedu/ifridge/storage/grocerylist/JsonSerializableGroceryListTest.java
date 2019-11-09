package seedu.ifridge.storage.grocerylist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ifridge.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.ifridge.commons.exceptions.IllegalValueException;
import seedu.ifridge.commons.util.JsonUtil;
import seedu.ifridge.model.GroceryList;
import seedu.ifridge.storage.JsonSerializableGroceryList;
import seedu.ifridge.testutil.TypicalGroceryItems;

public class JsonSerializableGroceryListTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableGroceryListTest");
    private static final Path TYPICAL_GROCERY_ITEMS_FILE =
            TEST_DATA_FOLDER.resolve("typicalGroceryItemsGroceryList.json");
    private static final Path INVALID_GROCERY_ITEM_FILE =
            TEST_DATA_FOLDER.resolve("invalidGroceryItemGroceryList.json");
    private static final Path DUPLICATE_GROCERY_ITEM_FILE =
            TEST_DATA_FOLDER.resolve("duplicateGroceryItemGroceryList.json");

    @Test
    public void toModelType_typicalGroceryItemsFile_success() throws Exception {
        JsonSerializableGroceryList dataFromFile = JsonUtil.readJsonFile(TYPICAL_GROCERY_ITEMS_FILE,
                JsonSerializableGroceryList.class).get();
        GroceryList groceryListFromFile = dataFromFile.toModelType();
        GroceryList typicalGroceryItemsGroceryList = TypicalGroceryItems.getTypicalGroceryList();
        assertEquals(groceryListFromFile, typicalGroceryItemsGroceryList);
    }

    @Test
    public void toModelType_invalidGroceryItemsFile_throwsIllegalValueException() throws Exception {
        JsonSerializableGroceryList dataFromFile = JsonUtil.readJsonFile(INVALID_GROCERY_ITEM_FILE,
                JsonSerializableGroceryList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateGroceryItems_throwsIllegalValueException() throws Exception {
        JsonSerializableGroceryList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_GROCERY_ITEM_FILE,
                JsonSerializableGroceryList.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableGroceryList.MESSAGE_DUPLICATE_GROCERY_ITEM,
                dataFromFile::toModelType);
    }

}
