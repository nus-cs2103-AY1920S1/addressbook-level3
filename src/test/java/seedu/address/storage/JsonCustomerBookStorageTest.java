package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCustomers.ALICE;
import static seedu.address.testutil.TypicalCustomers.HARRY;
import static seedu.address.testutil.TypicalCustomers.INDY;
import static seedu.address.testutil.TypicalCustomers.getTypicalCustomerBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.DataBook;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.customer.Customer;

public class JsonCustomerBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonCustomerBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readCustomerBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCustomerBook(null));
    }

    private java.util.Optional<ReadOnlyDataBook<Customer>> readCustomerBook(String filePath) throws Exception {
        return new JsonCustomerBookStorage(Paths.get(filePath)).readCustomerBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCustomerBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readCustomerBook("notJsonFormatCustomerBook.json"));
    }

    @Test
    public void readCustomerBook_invalidCustomerCustomerBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCustomerBook("invalidCustomerCustomerBook.json"));
    }

    @Test
    public void readCustomerBook_invalidAndValidCustomerCustomerBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCustomerBook("invalidAndValidCustomerCustomerBook.json"));
    }

    @Test
    public void readAndSaveCustomerBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempCustomerBook.json");
        DataBook<Customer> original = getTypicalCustomerBook();
        JsonCustomerBookStorage jsonCustomerBookStorage = new JsonCustomerBookStorage(filePath);

        // Save in new file and read back
        jsonCustomerBookStorage.saveCustomerBook(original, filePath);
        ReadOnlyDataBook readBack = jsonCustomerBookStorage.readCustomerBook(filePath).get();
        assertEquals(original, new DataBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.add(HARRY);
        original.remove(ALICE);
        jsonCustomerBookStorage.saveCustomerBook(original, filePath);
        readBack = jsonCustomerBookStorage.readCustomerBook(filePath).get();
        assertEquals(original, new DataBook<Customer>(readBack));

        // Save and read without specifying file path
        original.add(INDY);
        jsonCustomerBookStorage.saveCustomerBook(original); // file path not specified
        readBack = jsonCustomerBookStorage.readCustomerBook().get(); // file path not specified
        assertEquals(original, new DataBook(readBack));

    }

    @Test
    public void saveCustomerBook_nullCustomerBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCustomerBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code customerBook} at the specified {@code filePath}.
     */
    private void saveCustomerBook(ReadOnlyDataBook customerBook, String filePath) {
        try {
            new JsonCustomerBookStorage(Paths.get(filePath))
                    .saveCustomerBook(customerBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCustomerBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCustomerBook(new DataBook(), null));
    }
}