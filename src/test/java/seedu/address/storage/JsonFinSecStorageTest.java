package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalObjects.ALICE;
import static seedu.address.testutil.TypicalObjects.HOON;
import static seedu.address.testutil.TypicalObjects.IDA;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSec;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.FinSec;
import seedu.address.model.ReadOnlyFinSec;

public class JsonFinSecStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonFinSecStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyFinSec> readAddressBook(String filePath) throws Exception {
        return new JsonFinSecStorage(Paths.get(filePath)).readContacts(addToTestDataPathIfNotNull(filePath));
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
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidPersonAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        FinSec original = getTypicalFinSec();
        JsonFinSecStorage jsonAddressBookStorage = new JsonFinSecStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveFinSec(original, filePath);
        ReadOnlyFinSec readBack = jsonAddressBookStorage.readContacts(filePath).get();
        assertEquals(original, new FinSec(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addContact(HOON);
        original.removeContact(ALICE);
        jsonAddressBookStorage.saveFinSec(original, filePath);
        readBack = jsonAddressBookStorage.readContacts(filePath).get();
        assertEquals(original, new FinSec(readBack));

        // Save and read without specifying file path
        original.addContact(IDA);
        jsonAddressBookStorage.saveFinSec(original); // file path not specified
        readBack = jsonAddressBookStorage.readContacts().get(); // file path not specified
        assertEquals(original, new FinSec(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyFinSec addressBook, String filePath) {
        try {
            new JsonFinSecStorage(Paths.get(filePath))
                    .saveFinSec(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new FinSec(), null));
    }
}
