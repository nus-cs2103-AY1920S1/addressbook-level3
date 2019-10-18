package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSpendings.ALICE;
import static seedu.address.testutil.TypicalSpendings.HOON;
import static seedu.address.testutil.TypicalSpendings.IDA;
import static seedu.address.testutil.TypicalSpendings.getTypicalSpendingBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlySpendingBook;
import seedu.address.model.SpendingBook;

public class JsonSpendingBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlySpendingBook> readAddressBook(String filePath) throws Exception {
        return new JsonSpendingBookStorage(Paths.get(filePath)).readSpendingBook(addToTestDataPathIfNotNull(filePath));
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

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidSpendingAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidSpendingAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidSpendingAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidAndValidSpendingAddressBook.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        SpendingBook original = getTypicalSpendingBook();
        JsonSpendingBookStorage jsonSpendingBookStorage = new JsonSpendingBookStorage(filePath);

        // Save in new file and read back
        jsonSpendingBookStorage.saveSpendingBook(original, filePath);
        ReadOnlySpendingBook readBack = jsonSpendingBookStorage.readSpendingBook(filePath).get();
        assertEquals(original, new SpendingBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addSpending(HOON);
        original.removeSpending(ALICE);
        jsonSpendingBookStorage.saveSpendingBook(original, filePath);
        readBack = jsonSpendingBookStorage.readSpendingBook(filePath).get();
        assertEquals(original, new SpendingBook(readBack));

        // Save and read without specifying file path
        original.addSpending(IDA);
        jsonSpendingBookStorage.saveSpendingBook(original); // file path not specified
        readBack = jsonSpendingBookStorage.readSpendingBook().get(); // file path not specified
        assertEquals(original, new SpendingBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlySpendingBook addressBook, String filePath) {
        try {
            new JsonSpendingBookStorage(Paths.get(filePath))
                    .saveSpendingBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new SpendingBook(), null));
    }
}
