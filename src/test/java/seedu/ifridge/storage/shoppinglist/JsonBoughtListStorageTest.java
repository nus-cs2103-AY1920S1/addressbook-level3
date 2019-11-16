package seedu.ifridge.storage.shoppinglist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.ifridge.testutil.Assert.assertThrows;
import static seedu.ifridge.testutil.TypicalBoughtList.CAKE;
import static seedu.ifridge.testutil.TypicalBoughtList.OLIVE_OIL;
import static seedu.ifridge.testutil.TypicalBoughtList.getTypicalBoughtList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.ifridge.commons.exceptions.DataConversionException;
import seedu.ifridge.model.GroceryList;
import seedu.ifridge.model.ReadOnlyGroceryList;

public class JsonBoughtListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonBoughtListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readBoughtList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readBoughtList(null));
    }

    private java.util.Optional<ReadOnlyGroceryList> readBoughtList(String filePath) throws Exception {
        return new JsonBoughtListStorage(Paths.get(filePath)).readBoughtList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readBoughtList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readBoughtList("notJsonFormatBoughtList.json"));
    }

    @Test
    public void readBoughtList_invalidGroceryItemBoughtList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readBoughtList("invalidBoughtItemBoughtList.json"));
    }

    @Test
    public void readBoughtList_invalidAndValidGroceryItemBoughtList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readBoughtList(
                "invalidAndValidBoughtItemBoughtList.json"));
    }

    @Test
    public void readAndSaveBoughtList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempBoughtList.json");
        GroceryList original = getTypicalBoughtList();
        JsonBoughtListStorage jsonBoughtListStorage = new JsonBoughtListStorage(filePath);

        // Save in new file and read back
        jsonBoughtListStorage.saveBoughtList(original, filePath);
        ReadOnlyGroceryList readBack = jsonBoughtListStorage.readBoughtList(filePath).get();
        assertEquals(original, new GroceryList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addGroceryItem(CAKE);
        original.removeGroceryItem(OLIVE_OIL);
        jsonBoughtListStorage.saveBoughtList(original, filePath);
        readBack = jsonBoughtListStorage.readBoughtList(filePath).get();
        assertEquals(original, new GroceryList(readBack));

        // Save and read without specifying file path
        original.addGroceryItem(OLIVE_OIL);
        jsonBoughtListStorage.saveBoughtList(original); // file path not specified
        readBack = jsonBoughtListStorage.readBoughtList().get(); // file path not specified
        assertEquals(original, new GroceryList(readBack));
    }

    @Test
    public void saveBoughtList_nullBoughtList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveBoughtList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code boughtList} at the specified {@code filePath}.
     */
    private void saveBoughtList(ReadOnlyGroceryList boughtList, String filePath) {
        try {
            new JsonBoughtListStorage(Paths.get(filePath))
                    .saveBoughtList(boughtList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveBoughtList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveBoughtList(new GroceryList(), null));
    }
}
