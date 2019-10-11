package seedu.savenus.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalFood.BEE_HOON;
import static seedu.savenus.testutil.TypicalFood.CARBONARA;
import static seedu.savenus.testutil.TypicalFood.FISHBALL_NOODLES;
import static seedu.savenus.testutil.TypicalFood.getTypicalMenu;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.model.Menu;
import seedu.savenus.model.ReadOnlyMenu;

public class JsonMenuStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonMenuStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readMenu_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readMenu(null));
    }

    private java.util.Optional<ReadOnlyMenu> readMenu(String filePath) throws Exception {
        return new JsonMenuStorage(Paths.get(filePath)).readMenu(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readMenu("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readMenu("notJsonFormatMenu.json"));
    }

    @Test
    public void readMenu_invalidfoodMenu_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readMenu("invalidFoodMenu.json"));
    }

    @Test
    public void readMenu_invalidAndValidfoodMenu_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readMenu("invalidAndValidFoodMenu.json"));
    }

    @Test
    public void readAndSaveMenu_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempMenu.json");
        Menu original = getTypicalMenu();
        JsonMenuStorage jsonMenuStorage = new JsonMenuStorage(filePath);

        // Save in new file and read back
        jsonMenuStorage.saveMenu(original, filePath);
        ReadOnlyMenu readBack = jsonMenuStorage.readMenu(filePath).get();
        assertEquals(original, new Menu(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addFood(BEE_HOON);
        original.removeFood(CARBONARA);
        jsonMenuStorage.saveMenu(original, filePath);
        readBack = jsonMenuStorage.readMenu(filePath).get();
        assertEquals(original, new Menu(readBack));

        // Save and read without specifying file path
        original.addFood(FISHBALL_NOODLES);
        jsonMenuStorage.saveMenu(original); // file path not specified
        readBack = jsonMenuStorage.readMenu().get(); // file path not specified
        assertEquals(original, new Menu(readBack));

    }

    @Test
    public void saveMenu_nullMenu_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveMenu(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveMenu(ReadOnlyMenu addressBook, String filePath) {
        try {
            new JsonMenuStorage(Paths.get(filePath))
                    .saveMenu(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveMenu_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveMenu(new Menu(), null));
    }
}
