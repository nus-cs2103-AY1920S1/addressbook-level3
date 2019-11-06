package dukecooks.storage.mealplan;

import static dukecooks.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.commons.util.JsonUtil;
import dukecooks.model.mealplan.MealPlanBook;
import dukecooks.testutil.mealplan.TypicalMealPlans;

public class JsonSerializableMealPlanBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableMealPlanBookTest");
    private static final Path TYPICAL_MEALPLANS_FILE = TEST_DATA_FOLDER.resolve("typicalMealPlansMealPlanBook.json");
    private static final Path INVALID_MEALPLAN_FILE = TEST_DATA_FOLDER.resolve("invalidMealPlanMealPlanBook.json");
    private static final Path DUPLICATE_MEALPLAN_FILE = TEST_DATA_FOLDER.resolve("duplicateMealPlanMealPlanBook.json");

    @Test
    public void toModelType_typicalMealPlansFile_success() throws Exception {
        JsonSerializableMealPlanBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_MEALPLANS_FILE,
                JsonSerializableMealPlanBook.class).get();
        MealPlanBook mealPlanBookFromFile = dataFromFile.toModelType();
        MealPlanBook typicalMealPlansMealPlanBook = TypicalMealPlans.getTypicalMealPlanBook();
        assertEquals(mealPlanBookFromFile, typicalMealPlansMealPlanBook);
    }

    @Test
    public void toModelType_invalidMealPlanFile_throwsIllegalValueException() throws Exception {
        JsonSerializableMealPlanBook dataFromFile = JsonUtil.readJsonFile(INVALID_MEALPLAN_FILE,
                JsonSerializableMealPlanBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateMealPlans_throwsIllegalValueException() throws Exception {
        JsonSerializableMealPlanBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MEALPLAN_FILE,
                JsonSerializableMealPlanBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableMealPlanBook.MESSAGE_DUPLICATE_MEALPLAN,
                dataFromFile::toModelType);
    }
}
