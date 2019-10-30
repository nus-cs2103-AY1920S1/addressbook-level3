package dukecooks.storage.mealplan;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import dukecooks.commons.util.JsonUtil;
import dukecooks.model.mealplan.MealPlanBook;
import dukecooks.testutil.mealplan.TypicalMealPlans;

public class JsonSerializableMealPlanBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableMealPlanBookTest");
    private static final Path TYPICAL_MEALPLANS_FILE = TEST_DATA_FOLDER.resolve("typicalMealPlansMealPlanBook.json");

    @Test
    public void toModelType_typicalMealPlansFile_success() throws Exception {
        JsonSerializableMealPlanBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_MEALPLANS_FILE,
                JsonSerializableMealPlanBook.class).get();
        MealPlanBook mealPlanBookFromFile = dataFromFile.toModelType();
        MealPlanBook typicalMealPlansMealPlanBook = TypicalMealPlans.getTypicalMealPlanBook();
        assertEquals(mealPlanBookFromFile, typicalMealPlansMealPlanBook);
    }
}
