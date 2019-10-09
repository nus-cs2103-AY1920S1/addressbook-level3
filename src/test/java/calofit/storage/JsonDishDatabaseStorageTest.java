package calofit.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import calofit.commons.exceptions.DataConversionException;
import calofit.model.dish.DishDatabase;
import calofit.model.dish.ReadOnlyDishDatabase;
import calofit.testutil.Assert;
import calofit.testutil.TypicalDishes;

public class JsonDishDatabaseStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonDishDatabaseStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readDishDatabase_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readDishDatabase(null));
    }

    private java.util.Optional<ReadOnlyDishDatabase> readDishDatabase(String filePath) throws Exception {
        return new JsonDishDatabaseStorage(Paths.get(filePath)).readDishDatabase(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readDishDatabase("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataConversionException.class, () -> readDishDatabase("notJsonFormatDishDatabase.json"));
    }

    @Test
    public void readDishDatabase_invalidDishDishDatabase_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readDishDatabase("invalidDishDatabase.json"));
    }

    @Test
    public void readAndSaveDishDatabase_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempDishDatabase.json");
        DishDatabase original = TypicalDishes.getTypicalDishDatabase();
        JsonDishDatabaseStorage jsonDishDatabaseStorage = new JsonDishDatabaseStorage(filePath);

        // Save in new file and read back
        jsonDishDatabaseStorage.saveDishDatabase(original, filePath);
        ReadOnlyDishDatabase readBack = jsonDishDatabaseStorage.readDishDatabase(filePath).get();
        assertEquals(original, new DishDatabase(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addDish(TypicalDishes.HOON);
        original.removeDish(TypicalDishes.ALICE);
        jsonDishDatabaseStorage.saveDishDatabase(original, filePath);
        readBack = jsonDishDatabaseStorage.readDishDatabase(filePath).get();
        assertEquals(original, new DishDatabase(readBack));

        // Save and read without specifying file path
        original.addDish(TypicalDishes.IDA);
        jsonDishDatabaseStorage.saveDishDatabase(original); // file path not specified
        readBack = jsonDishDatabaseStorage.readDishDatabase().get(); // file path not specified
        assertEquals(original, new DishDatabase(readBack));

    }

    @Test
    public void saveDishDatabase_nullDishDatabase_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveDishDatabase(null, "SomeFile.json"));
    }

    /**
     * Saves {@code dishDatabase} at the specified {@code filePath}.
     */
    private void saveDishDatabase(ReadOnlyDishDatabase dishDatabase, String filePath) {
        try {
            new JsonDishDatabaseStorage(Paths.get(filePath))
                    .saveDishDatabase(dishDatabase, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveDishDatabase_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveDishDatabase(new DishDatabase(), null));
    }
}
