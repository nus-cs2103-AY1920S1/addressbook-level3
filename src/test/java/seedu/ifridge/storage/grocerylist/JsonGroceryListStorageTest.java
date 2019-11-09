package seedu.ifridge.storage.grocerylist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.ifridge.testutil.Assert.assertThrows;
import static seedu.ifridge.testutil.TypicalGroceryItems.CAKE;
import static seedu.ifridge.testutil.TypicalGroceryItems.RICE_WINE;
import static seedu.ifridge.testutil.TypicalGroceryItems.SPAGHETTI;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.ifridge.commons.exceptions.DataConversionException;
import seedu.ifridge.model.GroceryList;
import seedu.ifridge.model.ReadOnlyGroceryList;
import seedu.ifridge.storage.JsonGroceryListStorage;
import seedu.ifridge.testutil.TypicalGroceryItems;

public class JsonGroceryListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonGroceryListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readGroceryList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readGroceryList(null));
    }

    private java.util.Optional<ReadOnlyGroceryList> readGroceryList(String filePath) throws Exception {
        return new JsonGroceryListStorage(Paths.get(filePath)).readGroceryList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readGroceryList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readGroceryList("notJsonFormatGroceryList.json"));
    }

    @Test
    public void readGroceryList_invalidGroceryItemGroceryList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readGroceryList("invalidGroceryItemGroceryList.json"));
    }

    @Test
    public void readGroceryList_invalidAndValidGroceryItemGroceryList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
                readGroceryList("invalidAndValidGroceryItemGroceryList.json"));
    }

    @Test
    public void readAndSaveGroceryList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempGroceryList.json");
        GroceryList original = TypicalGroceryItems.getTypicalGroceryList();
        JsonGroceryListStorage jsonGroceryListStorage = new JsonGroceryListStorage(filePath);

        // Save in new file and read back
        jsonGroceryListStorage.saveGroceryList(original, filePath);
        ReadOnlyGroceryList readBack = jsonGroceryListStorage.readGroceryList(filePath).get();
        assertEquals(original, new GroceryList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addGroceryItem(RICE_WINE);
        original.removeGroceryItem(SPAGHETTI);
        jsonGroceryListStorage.saveGroceryList(original, filePath);
        readBack = jsonGroceryListStorage.readGroceryList(filePath).get();
        assertEquals(original, new GroceryList(readBack));

        // Save and read without specifying file path
        original.addGroceryItem(CAKE);
        jsonGroceryListStorage.saveGroceryList(original); // file path not specified
        readBack = jsonGroceryListStorage.readGroceryList().get(); // file path not specified
        assertEquals(original, new GroceryList(readBack));

    }

    @Test
    public void saveGroceryList_nullGroceryList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveGroceryList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveGroceryList(ReadOnlyGroceryList groceryList, String filePath) {
        try {
            new JsonGroceryListStorage(Paths.get(filePath))
                    .saveGroceryList(groceryList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveGroceryList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveGroceryList(new GroceryList(), null));
    }
}
