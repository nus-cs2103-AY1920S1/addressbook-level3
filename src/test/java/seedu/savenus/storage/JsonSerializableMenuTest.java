package seedu.savenus.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.commons.util.JsonUtil;
import seedu.savenus.model.Menu;
import seedu.savenus.testutil.TypicalFood;

public class JsonSerializableMenuTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableMenuTest");
    private static final Path TYPICAL_foodS_FILE = TEST_DATA_FOLDER.resolve("typicalFoodMenu.json");
    private static final Path INVALID_food_FILE = TEST_DATA_FOLDER.resolve("invalidFoodMenu.json");
    private static final Path DUPLICATE_food_FILE = TEST_DATA_FOLDER.resolve("duplicateFoodMenu.json");

    @Test
    public void toModelType_invalidfoodFile_throwsIllegalValueException() throws Exception {
        JsonSerializableMenu dataFromFile = JsonUtil.readJsonFile(INVALID_food_FILE,
            JsonSerializableMenu.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_typicalfoodsFile_success() throws Exception {
        JsonSerializableMenu dataFromFile = JsonUtil.readJsonFile(TYPICAL_foodS_FILE,
            JsonSerializableMenu.class).get();
        Menu addressBookFromFile = dataFromFile.toModelType();
        Menu typicalfoodsMenu = TypicalFood.getTypicalMenu();
        assertEquals(addressBookFromFile, typicalfoodsMenu);
    }

    @Test
    public void toModelType_duplicatefoods_throwsIllegalValueException() throws Exception {
        JsonSerializableMenu dataFromFile = JsonUtil.readJsonFile(DUPLICATE_food_FILE,
            JsonSerializableMenu.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableMenu.MESSAGE_DUPLICATE_FOOD,
            dataFromFile::toModelType);
    }

}
