package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.JsonUtil;
import seedu.address.model.RecipeBook;
import seedu.address.testutil.TypicalRecipes;

public class JsonSerializableRecipeBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableRecipeBookTest");
    private static final Path TYPICAL_RECIPES_FILE = TEST_DATA_FOLDER.resolve("typicalRecipesRecipeBook.json");

    @Test
    public void toModelType_typicalRecipesFile_success() throws Exception {
        JsonSerializableRecipeBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_RECIPES_FILE,
                JsonSerializableRecipeBook.class).get();
        RecipeBook recipeBookFromFile = dataFromFile.toModelType();
        RecipeBook typicalRecipesRecipeBook = TypicalRecipes.getTypicalRecipeBook();
        assertEquals(recipeBookFromFile, typicalRecipesRecipeBook);
    }
}
