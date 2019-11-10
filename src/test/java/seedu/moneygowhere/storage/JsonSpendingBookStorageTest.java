package seedu.moneygowhere.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.moneygowhere.testutil.Assert.assertThrows;
import static seedu.moneygowhere.testutil.TypicalSpendings.APPLE;
import static seedu.moneygowhere.testutil.TypicalSpendings.BILL_REMINDER;
import static seedu.moneygowhere.testutil.TypicalSpendings.HAT;
import static seedu.moneygowhere.testutil.TypicalSpendings.ICECREAM;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.moneygowhere.commons.exceptions.DataConversionException;
import seedu.moneygowhere.model.ReadOnlySpendingBook;
import seedu.moneygowhere.model.SpendingBook;

public class JsonSpendingBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSpendingBookStorageTest");

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
        assertThrows(DataConversionException.class, () -> readAddressBook("notJsonFormatSpendingBook.json"));
    }

    @Test
    public void readAddressBook_invalidSpendingAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidSpendingSpendingBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidSpendingAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidAndValidSpendingSpendingBook.json"));
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
        original.addSpending(HAT);
        original.removeSpending(APPLE);
        original.removeReminder(BILL_REMINDER);
        jsonSpendingBookStorage.saveSpendingBook(original, filePath);
        readBack = jsonSpendingBookStorage.readSpendingBook(filePath).get();
        assertEquals(original, new SpendingBook(readBack));

        // Save and read without specifying file path
        original.addSpending(ICECREAM);
        jsonSpendingBookStorage.saveSpendingBook(original); // file path not specified
        readBack = jsonSpendingBookStorage.readSpendingBook().get(); // file path not specified
        assertEquals(original, new SpendingBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveSpendingBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveSpendingBook(ReadOnlySpendingBook spendingBook, String filePath) {
        try {
            new JsonSpendingBookStorage(Paths.get(filePath))
                    .saveSpendingBook(spendingBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveSpendingBook(new SpendingBook(), null));
    }
}
