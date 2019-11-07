package calofit.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import calofit.commons.exceptions.IllegalValueException;
import calofit.commons.util.JsonUtil;
import calofit.model.meal.MealLog;
import calofit.testutil.Assert;
import calofit.testutil.TypicalMeals;

public class JsonSerializableMealLogTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableMealLogTest");
    private static final Path TYPICAL_MEALS_FILE = TEST_DATA_FOLDER.resolve("typicalMealLog.json");
    private static final Path INVALID_MEALS_FILE = TEST_DATA_FOLDER.resolve("invalidMealLog.json");
    private static final Path DUPLICATE_MEALS_FILE = TEST_DATA_FOLDER.resolve("duplicateMealLog.json");

    @Test
    public void toModelType_duplicateMeals_throwsIllegalValueException() throws Exception {
        JsonSerializableMealLog dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MEALS_FILE,
                JsonSerializableMealLog.class).get();
        Assert.assertThrows(IllegalValueException.class, JsonSerializableMealLog.MESSAGE_DUPLICATE_MEALS,
                dataFromFile::toModelType);
    }
}
