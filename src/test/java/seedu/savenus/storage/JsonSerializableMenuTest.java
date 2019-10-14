package seedu.savenus.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.commons.util.JsonUtil;
import seedu.savenus.model.Menu;
import seedu.savenus.testutil.TypicalMenu;

public class JsonSerializableMenuTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableMenuTest");
    private static final Path TYPICAL_FOOD_FILE = TEST_DATA_FOLDER.resolve("typicalFoodMenu.json");
    private static final Path INVALID_FOOD_FILE = TEST_DATA_FOLDER.resolve("invalidFoodMenu.json");
    private static final Path INVALID_WALLET_FILE = TEST_DATA_FOLDER.resolve("invalidWalletMenu.json");
    private static final Path INVALID_PURCHASE_FILE = TEST_DATA_FOLDER.resolve("invalidPurchaseMenu.json");
    private static final Path DUPLICATE_FOOD_FILE = TEST_DATA_FOLDER.resolve("duplicateFoodMenu.json");

    @Test
    public void toModelType_invalidFoodMenu_throwsIllegalValueException() throws Exception {
        JsonSerializableMenu dataFromFile = JsonUtil.readJsonFile(INVALID_FOOD_FILE,
            JsonSerializableMenu.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
    @Test
    public void toModelType_invalidWalletMenu_throwsIllegalValueException() throws Exception {
        JsonSerializableMenu dataFromFile = JsonUtil.readJsonFile(INVALID_WALLET_FILE,
                JsonSerializableMenu.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
    @Test
    public void toModelType_invalidPurchaseMenu_throwsIllegalValueException() throws Exception {
        JsonSerializableMenu dataFromFile = JsonUtil.readJsonFile(INVALID_PURCHASE_FILE,
                JsonSerializableMenu.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_typicalFoodMenu_success() throws Exception {
        JsonSerializableMenu dataFromFile = JsonUtil.readJsonFile(TYPICAL_FOOD_FILE,
            JsonSerializableMenu.class).get();
        Menu menuFromFile = dataFromFile.toModelType();
        Menu typicalMenu = TypicalMenu.getTypicalMenu();
        assertEquals(menuFromFile, typicalMenu);
    }

    @Test
    public void toModelType_duplicateFood_throwsIllegalValueException() throws Exception {
        JsonSerializableMenu dataFromFile = JsonUtil.readJsonFile(DUPLICATE_FOOD_FILE,
            JsonSerializableMenu.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableMenu.MESSAGE_DUPLICATE_FOOD,
            dataFromFile::toModelType);
    }

}
