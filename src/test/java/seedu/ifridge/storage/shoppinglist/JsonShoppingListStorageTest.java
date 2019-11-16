package seedu.ifridge.storage.shoppinglist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.ifridge.testutil.Assert.assertThrows;
import static seedu.ifridge.testutil.TypicalShoppingList.CAKE;
import static seedu.ifridge.testutil.TypicalShoppingList.SPAGHETTI;
import static seedu.ifridge.testutil.TypicalShoppingList.getTypicalShoppingList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.ifridge.commons.exceptions.DataConversionException;
import seedu.ifridge.model.ReadOnlyShoppingList;
import seedu.ifridge.model.ShoppingList;

public class JsonShoppingListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonShoppingListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readShoppingList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readShoppingList(null));
    }

    private java.util.Optional<ReadOnlyShoppingList> readShoppingList(String filePath) throws Exception {
        return new JsonShoppingListStorage(Paths.get(filePath)).readShoppingList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readShoppingList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readShoppingList("notJsonFormatShoppingList.json"));
    }

    @Test
    public void readShoppingList_invalidShoppingItemShoppingList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readShoppingList("invalidShoppingItemShoppingList.json"));
    }

    @Test
    public void readShoppingList_invalidAndValidShoppingItemShoppingList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readShoppingList(
                "invalidAndValidShoppingItemShoppingList.json"));
    }

    @Test
    public void readAndSaveShoppingList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempShoppingList.json");
        ShoppingList original = getTypicalShoppingList();
        JsonShoppingListStorage jsonShoppingListStorage = new JsonShoppingListStorage(filePath);

        // Save in new file and read back
        jsonShoppingListStorage.saveShoppingList(original, filePath);
        ReadOnlyShoppingList readBack = jsonShoppingListStorage.readShoppingList(filePath).get();
        assertEquals(original, new ShoppingList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addShoppingItem(SPAGHETTI);
        original.removeShoppingItem(CAKE);
        jsonShoppingListStorage.saveShoppingList(original, filePath);
        readBack = jsonShoppingListStorage.readShoppingList(filePath).get();
        assertEquals(original, new ShoppingList(readBack));

        // Save and read without specifying file path
        original.addShoppingItem(CAKE);
        jsonShoppingListStorage.saveShoppingList(original); // file path not specified
        readBack = jsonShoppingListStorage.readShoppingList().get(); // file path not specified
        assertEquals(original, new ShoppingList(readBack));
    }

    @Test
    public void saveShoppingList_nullShoppingList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveShoppingList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code shoppingList} at the specified {@code filePath}.
     */
    private void saveShoppingList(ReadOnlyShoppingList shoppingList, String filePath) {
        try {
            new JsonShoppingListStorage(Paths.get(filePath))
                    .saveShoppingList(shoppingList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveShoppingList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveShoppingList(new ShoppingList(), null));
    }
}
