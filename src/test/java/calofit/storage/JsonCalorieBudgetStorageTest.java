package calofit.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import calofit.commons.exceptions.DataConversionException;
import calofit.model.CalorieBudget;
import calofit.model.util.SampleDataUtil;
import calofit.testutil.Assert;

class JsonCalorieBudgetStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonCalorieBudgetStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readMealLog_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readBudget(null));
    }

    private java.util.Optional<CalorieBudget> readBudget(String filePath) throws Exception {
        return new JsonCalorieBudgetStorage(Paths.get(filePath))
            .readCalorieBudget(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
            : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readBudget("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataConversionException.class, () -> readBudget("notJsonFormatBudget.json"));
    }

    @Test
    public void readMealLog_invalidMealLog_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readBudget("invalidBudget.json"));
    }

    @Test
    public void readAndSaveBudget_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempMealLog.json");
        CalorieBudget budget = SampleDataUtil.getSampleBudget();
        JsonCalorieBudgetStorage storage = new JsonCalorieBudgetStorage(filePath);

        assertEquals(filePath, storage.getCalorieBudgetFilePath());

        // Save in new file and read back
        storage.saveCalorieBudget(budget, filePath);
        CalorieBudget readBack = storage.readCalorieBudget().get();
        assertEquals(budget, readBack);

        // Modify data, overwrite exiting file, and read back
        budget.setCurrentBudget(14000);
        storage.saveCalorieBudget(budget, filePath);
        readBack = storage.readCalorieBudget().get();
        assertEquals(budget, readBack);

        // Save and read without specifying file path
        budget.setCurrentBudget(28000);
        storage.saveCalorieBudget(budget);
        readBack = storage.readCalorieBudget().get();
        assertEquals(budget, readBack);
    }

    @Test
    public void saveMealLog_nullMealLog_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveBudget(null, "SomeFile.json"));
    }

    /**
     * Saves {@code budget} at the specified {@code filePath}.
     */
    private void saveBudget(CalorieBudget budget, String filePath) {
        try {
            new JsonCalorieBudgetStorage(Paths.get(filePath))
                .saveCalorieBudget(budget, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveMealLog_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
            saveBudget(SampleDataUtil.getSampleBudget(), null));
    }
}
