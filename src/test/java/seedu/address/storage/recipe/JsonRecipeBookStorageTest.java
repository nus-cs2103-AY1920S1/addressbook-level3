package seedu.address.storage.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.recipe.TypicalRecipes.KAPPA;
import static seedu.address.testutil.recipe.TypicalRecipes.MILO;
import static seedu.address.testutil.recipe.TypicalRecipes.TAMAGO;
import static seedu.address.testutil.recipe.TypicalRecipes.getTypicalRecipeBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.recipe.ReadOnlyRecipeBook;
import seedu.address.model.recipe.RecipeBook;

public class JsonRecipeBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonRecipeBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readRecipeBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readRecipeBook(null));
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
        assertThrows(DataConversionException.class, () -> readRecipeBook("notJsonFormatRecipeBook.json"));
    }

    @Test
    public void readRecipeBook_invalidRecipeRecipeBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readRecipeBook("invalidRecipeRecipeBook.json"));
    }

    @Test
    public void readRecipeBook_invalidAndValidRecipeRecipeBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readRecipeBook("invalidAndValidRecipeRecipeBook.json"));
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
        assertThrows(NullPointerException.class, () -> saveRecipeBook(null, "SomeFile.json"));
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
        assertThrows(NullPointerException.class, () -> saveRecipeBook(new RecipeBook(), null));
    }
}
