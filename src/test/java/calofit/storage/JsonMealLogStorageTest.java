package calofit.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import calofit.commons.exceptions.DataConversionException;
import calofit.model.meal.MealLog;
import calofit.model.meal.ReadOnlyMealLog;
import calofit.testutil.Assert;
import calofit.testutil.TypicalMeals;

public class JsonMealLogStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonMealLogStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readMealLog_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readMealLog(null));
    }

    private java.util.Optional<ReadOnlyMealLog> readMealLog(String filePath) throws Exception {
        return new JsonMealLogStorage(Paths.get(filePath)).readMealLog(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readMealLog("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataConversionException.class, () -> readMealLog("notJsonFormatMealLog.json"));
    }

    @Test
    public void readMealLog_invalidMealLog_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readMealLog("invalidMealLog.json"));
    }

    @Test
    public void readAndSaveMealLog_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempMealLog.json");
        MealLog original = TypicalMeals.getTypicalMealLog();
        JsonMealLogStorage jsonMealLogStorage = new JsonMealLogStorage(filePath);

        // Save in new file and read back
        jsonMealLogStorage.saveMealLog(original, filePath);
        ReadOnlyMealLog readBack = jsonMealLogStorage.readMealLog(filePath).get();
        assertEquals(original, new MealLog(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addMeal(TypicalMeals.CEREAL);
        original.removeMeal(TypicalMeals.SPAGHETTI);
        jsonMealLogStorage.saveMealLog(original, filePath);
        readBack = jsonMealLogStorage.readMealLog(filePath).get();
        assertEquals(original, new MealLog(readBack));

        // Save and read without specifying file path
        original.addMeal(TypicalMeals.STEAK);
        jsonMealLogStorage.saveMealLog(original); // file path not specified
        readBack = jsonMealLogStorage.readMealLog().get(); // file path not specified
        assertEquals(original, new MealLog(readBack));

    }

    @Test
    public void saveMealLog_nullMealLog_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveMealLog(null, "SomeFile.json"));
    }

    /**
     * Saves {@code mealLog} at the specified {@code filePath}.
     */
    private void saveMealLog(ReadOnlyMealLog mealLog, String filePath) {
        try {
            new JsonMealLogStorage(Paths.get(filePath))
                    .saveMealLog(mealLog, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveMealLog_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveMealLog(new MealLog(), null));
    }
}
