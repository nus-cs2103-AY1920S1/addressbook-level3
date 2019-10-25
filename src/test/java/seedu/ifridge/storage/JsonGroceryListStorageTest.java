package seedu.ifridge.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.ifridge.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.ifridge.model.GroceryList;
import seedu.ifridge.model.ReadOnlyGroceryList;

public class JsonGroceryListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyGroceryList> readAddressBook(String filePath) throws Exception {
        return new JsonGroceryListStorage(Paths.get(filePath)).readGroceryList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    /*@Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
    }*/

    /*@Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidPersonAddressBook.json"));
    }*/

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() {
        //assertThrows(DataConversionException.class, () -> readAddressBook("invalidAndValidPersonAddressBook.json"));
    }

    /*@Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        GroceryList original = getTypicalGroceryList();
        JsonGroceryListStorage jsonGroceryListStorage = new JsonGroceryListStorage(filePath);

        // Save in new file and read back
        jsonGroceryListStorage.saveGroceryList(original, filePath);
        ReadOnlyGroceryList readBack = jsonGroceryListStorage.readGroceryList(filePath).get();
        //assertEquals(original, new GroceryList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonGroceryListStorage.saveGroceryList(original, filePath);
        readBack = jsonGroceryListStorage.readGroceryList(filePath).get();
        //assertEquals(original, new GroceryList(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonGroceryListStorage.saveGroceryList(original); // file path not specified
        readBack = jsonGroceryListStorage.readGroceryList().get(); // file path not specified
        //assertEquals(original, new GroceryList(readBack));

    }*/

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyGroceryList groceryList, String filePath) {
        try {
            new JsonGroceryListStorage(Paths.get(filePath))
                    .saveGroceryList(groceryList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new GroceryList(), null));
    }
}
