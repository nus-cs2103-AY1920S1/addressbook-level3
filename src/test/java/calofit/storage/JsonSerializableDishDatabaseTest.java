package calofit.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import calofit.commons.exceptions.IllegalValueException;
import calofit.commons.util.JsonUtil;
import calofit.model.dish.DishDatabase;
import calofit.testutil.Assert;
import calofit.testutil.TypicalDishes;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

public class JsonSerializableDishDatabaseTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableDishDatabaseTest");
    private static final Path TYPICAL_DISHES_FILE = TEST_DATA_FOLDER.resolve("typicalDishDatabase.json");
    private static final Path INVALID_MEAL_FILE = TEST_DATA_FOLDER.resolve("invalidDishDatabase.json");
    private static final Path DUPLICATE_MEAL_FILE = TEST_DATA_FOLDER.resolve("duplicateDishDatabase.json");

    @Test
    public void toModelType_typicalDishesFile_success() throws Exception {
        JsonSerializableDishDatabase dataFromFile = JsonUtil.readJsonFile(TYPICAL_DISHES_FILE,
                JsonSerializableDishDatabase.class).get();
        DishDatabase dishDatabaseFromFile = dataFromFile.toModelType();
        DishDatabase typicalDishDatabase = TypicalDishes.getTypicalDishDatabase();
        assertEquals(dishDatabaseFromFile, typicalDishDatabase);
    }

    @Test
    public void toModelType_duplicateDishes_throwsIllegalValueException() throws Exception {
        JsonSerializableDishDatabase dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MEAL_FILE,
                JsonSerializableDishDatabase.class).get();
        Assert.assertThrows(IllegalValueException.class, JsonSerializableDishDatabase.MESSAGE_DUPLICATE_DISHES,
                dataFromFile::toModelType);
    }

}
