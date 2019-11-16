package seedu.ifridge.storage.shoppinglist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.ifridge.commons.exceptions.IllegalValueException;
import seedu.ifridge.commons.util.JsonUtil;
import seedu.ifridge.model.GroceryList;
import seedu.ifridge.testutil.TypicalBoughtList;

public class JsonSerializableBoughtListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableBoughtListTest");
    private static final Path TYPICAL_BOUGHT_ITEMS_FILE = TEST_DATA_FOLDER.resolve("typicalBoughtItemsBoughtList.json");
    private static final Path INVALID_BOUGHT_ITEM_FILE = TEST_DATA_FOLDER.resolve("invalidBoughtItemBoughtList.json");
    private static final Path DUPLICATE_BOUGHT_ITEM_FILE =
            TEST_DATA_FOLDER.resolve("duplicateBoughtItemBoughtList.json");

    @Test
    public void toModelType_typicalBoughtItemsFile_success() throws Exception {
        JsonSerializableBoughtList dataFromFile = JsonUtil.readJsonFile(TYPICAL_BOUGHT_ITEMS_FILE,
                JsonSerializableBoughtList.class).get();
        GroceryList groceryListFromFile = dataFromFile.toModelType();
        GroceryList typicalGroceryItemsBoughtList = TypicalBoughtList.getTypicalBoughtList();
        System.out.println(groceryListFromFile);
        System.out.println(typicalGroceryItemsBoughtList);
        assertEquals(groceryListFromFile, typicalGroceryItemsBoughtList);
    }

    @Test
    public void toModelType_invalidGroceryItemFile_throwsIllegalValueException() throws Exception {
        JsonSerializableBoughtList dataFromFile = JsonUtil.readJsonFile(INVALID_BOUGHT_ITEM_FILE,
                JsonSerializableBoughtList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateGroceryItems_throwsIllegalValueException() throws Exception {
        JsonSerializableBoughtList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_BOUGHT_ITEM_FILE,
                JsonSerializableBoughtList.class).get();
        assertThrows(IllegalValueException.class,
                dataFromFile::toModelType, JsonSerializableBoughtList.MESSAGE_DUPLICATE_BOUGHT_ITEMS);
    }

}
