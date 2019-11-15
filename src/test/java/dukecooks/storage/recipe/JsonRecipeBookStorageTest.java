package dukecooks.storage.recipe;

import static dukecooks.model.util.SampleRecipeDataUtil.getSampleRecipes;
import static dukecooks.testutil.recipe.TypicalRecipes.KAPPA;
import static dukecooks.testutil.recipe.TypicalRecipes.MILO;
import static dukecooks.testutil.recipe.TypicalRecipes.TAMAGO;
import static dukecooks.testutil.recipe.TypicalRecipes.getTypicalRecipeBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import dukecooks.commons.exceptions.DataConversionException;
import dukecooks.model.recipe.ReadOnlyRecipeBook;
import dukecooks.model.recipe.RecipeBook;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.testutil.Assert;

public class JsonRecipeBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonRecipeBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readRecipeBook_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readRecipeBook(null));
    }

    private java.util.Optional<ReadOnlyRecipeBook> readRecipeBook(String filePath) throws Exception {
        return new JsonRecipeBookStorage(Paths.get(filePath)).readRecipeBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readRecipeBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataConversionException.class, () -> readRecipeBook("notJsonFormatRecipeBook.json"));
    }

    @Test
    public void readRecipeBook_invalidRecipeRecipeBook_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, ()
            -> readRecipeBook("invalidRecipeRecipeBook.json"));
    }

    @Test
    public void readRecipeBook_invalidAndValidRecipeRecipeBook_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, ()
            -> readRecipeBook("invalidAndValidRecipeRecipeBook.json"));
    }

    @Test
    public void readAndSaveRecipeBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempRecipeBook.json");
        RecipeBook original = getTypicalRecipeBook();
        JsonRecipeBookStorage jsonRecipeBookStorage = new JsonRecipeBookStorage(filePath);

        // Save in new file and read back
        jsonRecipeBookStorage.saveRecipeBook(original, filePath);
        ReadOnlyRecipeBook readBack = jsonRecipeBookStorage.readRecipeBook(filePath).get();
        assertEquals(original, new RecipeBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addRecipe(TAMAGO);
        original.removeRecipe(MILO);
        jsonRecipeBookStorage.saveRecipeBook(original, filePath);
        readBack = jsonRecipeBookStorage.readRecipeBook(filePath).get();
        assertEquals(original, new RecipeBook(readBack));

        // Save and read without specifying file path
        original.addRecipe(KAPPA);
        jsonRecipeBookStorage.saveRecipeBook(original); // file path not specified
        readBack = jsonRecipeBookStorage.readRecipeBook().get(); // file path not specified
        assertEquals(original, new RecipeBook(readBack));

    }

    @Test
    public void saveRecipeBook_nullRecipeBook_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveRecipeBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code recipeBook} at the specified {@code filePath}.
     */
    private void saveRecipeBook(ReadOnlyRecipeBook recipeBook, String filePath) {
        try {
            new JsonRecipeBookStorage(Paths.get(filePath))
                    .saveRecipeBook(recipeBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveRecipeBook_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveRecipeBook(new RecipeBook(), null));
    }

    @Test
    public void sampleRecipeDataUtilTest() {
        Recipe[] expected = getSampleRecipes();

        assertTrue(expected != null);
    }
}
