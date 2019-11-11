package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ELLE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.GEORGE;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.TimeBook;
import seedu.address.testutil.TypicalTimeBook;
import seedu.address.testutil.personutil.PersonBuilder;

class JsonTimeBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonTimeBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTimeBookNullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTimeBook(null));
    }

    private java.util.Optional<TimeBook> readTimeBook(String filePath) throws Exception {
        return new JsonTimeBookStorage(Paths.get(filePath)).readTimeBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTimeBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readTimeBook("notJsonFormatTimeBook.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readTimeBook("invalidPersonTimeBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readTimeBook("invalidAndValidPersonTimeBook.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempTimeBook.json");
        TimeBook original = TypicalTimeBook.get();
        JsonTimeBookStorage jsonTimeBookStorage = new JsonTimeBookStorage(filePath);

        // Save in new file and read back
        jsonTimeBookStorage.saveTimeBook(original, filePath);
        TimeBook readBack = jsonTimeBookStorage.readTimeBook(filePath).get();
        assertEquals(original, readBack);

        // Modify data, overwrite exiting file, and read back
        original.addPerson(new PersonBuilder(GEORGE).build());
        original.getPersonList().deletePerson(ALICE.getPersonId());
        jsonTimeBookStorage.saveTimeBook(original, filePath);
        readBack = jsonTimeBookStorage.readTimeBook(filePath).get();
        assertEquals(original, readBack);

        // Save and read without specifying file path
        original.addPerson(new PersonBuilder(ELLE).build());
        jsonTimeBookStorage.saveTimeBook(original); // file path not specified
        readBack = jsonTimeBookStorage.readTimeBook().get(); // file path not specified
        assertEquals(original, readBack);

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            saveTimeBook(null, "SomeFile.json");
        });
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveTimeBook(TimeBook timeBook, String filePath) {
        try {
            new JsonTimeBookStorage(Paths.get(filePath))
                    .saveTimeBook(timeBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTimeBook(new TimeBook(), null));
    }
}
