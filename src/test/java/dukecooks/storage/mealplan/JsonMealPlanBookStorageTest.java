package dukecooks.storage.mealplan;

import static dukecooks.testutil.mealplan.TypicalMealPlans.KAPPA_MP;
import static dukecooks.testutil.mealplan.TypicalMealPlans.MILO_MP;
import static dukecooks.testutil.mealplan.TypicalMealPlans.TAMAGO_MP;
import static dukecooks.testutil.mealplan.TypicalMealPlans.getTypicalMealPlanBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import dukecooks.commons.exceptions.DataConversionException;
import dukecooks.model.mealplan.MealPlanBook;
import dukecooks.model.mealplan.ReadOnlyMealPlanBook;
import dukecooks.testutil.Assert;

public class JsonMealPlanBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonMealPlanBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readMealPlanBook_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readMealPlanBook(null));
    }

    private java.util.Optional<ReadOnlyMealPlanBook> readMealPlanBook(String filePath) throws Exception {
        return new JsonMealPlanBookStorage(Paths.get(filePath)).readMealPlanBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readMealPlanBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataConversionException.class, () -> readMealPlanBook("notJsonFormatMealPlanBook.json"));
    }

    @Test
    public void readMealPlanBook_invalidMealPlanMealPlanBook_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, ()
            -> readMealPlanBook("invalidMealPlanMealPlanBook.json"));
    }

    @Test
    public void readMealPlanBook_invalidAndValidMealPlanMealPlanBook_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, ()
            -> readMealPlanBook("invalidAndValidMealPlanMealPlanBook.json"));
    }

    @Test
    public void readAndSaveMealPlanBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempMealPlanBook.json");
        MealPlanBook original = getTypicalMealPlanBook();
        JsonMealPlanBookStorage jsonMealPlanBookStorage = new JsonMealPlanBookStorage(filePath);

        // Save in new file and read back
        jsonMealPlanBookStorage.saveMealPlanBook(original, filePath);
        ReadOnlyMealPlanBook readBack = jsonMealPlanBookStorage.readMealPlanBook(filePath).get();
        assertEquals(original, new MealPlanBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addMealPlan(TAMAGO_MP);
        original.removeMealPlan(MILO_MP);
        jsonMealPlanBookStorage.saveMealPlanBook(original, filePath);
        readBack = jsonMealPlanBookStorage.readMealPlanBook(filePath).get();
        assertEquals(original, new MealPlanBook(readBack));

        // Save and read without specifying file path
        original.addMealPlan(KAPPA_MP);
        jsonMealPlanBookStorage.saveMealPlanBook(original); // file path not specified
        readBack = jsonMealPlanBookStorage.readMealPlanBook().get(); // file path not specified
        assertEquals(original, new MealPlanBook(readBack));

    }

    @Test
    public void saveMealPlanBook_nullMealPlanBook_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveMealPlanBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code mealPlanBook} at the specified {@code filePath}.
     */
    private void saveMealPlanBook(ReadOnlyMealPlanBook mealPlanBook, String filePath) {
        try {
            new JsonMealPlanBookStorage(Paths.get(filePath))
                    .saveMealPlanBook(mealPlanBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveMealPlanBook_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveMealPlanBook(new MealPlanBook(), null));
    }
}
