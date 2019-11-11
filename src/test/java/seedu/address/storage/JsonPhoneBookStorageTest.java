package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPhones.IPHONE_JSON_TEST2;
import static seedu.address.testutil.TypicalPhones.IPHONE_JSON_TEST3;
import static seedu.address.testutil.TypicalPhones.IPHONEONE;
import static seedu.address.testutil.TypicalPhones.getTypicalPhoneBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.DataBook;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.phone.Phone;

public class JsonPhoneBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonPhoneBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readPhoneBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readPhoneBook(null));
    }

    private java.util.Optional<ReadOnlyDataBook<Phone>> readPhoneBook(String filePath) throws Exception {
        return new JsonPhoneBookStorage(Paths.get(filePath)).readPhoneBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readPhoneBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readPhoneBook("notJsonFormatPhoneBook.json"));
    }

    @Test
    public void readPhoneBook_invalidPhonePhoneBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readPhoneBook("invalidPhonePhoneBook.json"));
    }

    @Test
    public void readPhoneBook_invalidAndValidPhonePhoneBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readPhoneBook("invalidAndValidPhonePhoneBook.json"));
    }

    @Test
    public void readAndSavePhoneBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempPhoneBook.json");
        DataBook<Phone> original = getTypicalPhoneBook();
        JsonPhoneBookStorage jsonPhoneBookStorage = new JsonPhoneBookStorage(filePath);

        // Save in new file and read back
        jsonPhoneBookStorage.savePhoneBook(original, filePath);
        ReadOnlyDataBook readBack = jsonPhoneBookStorage.readPhoneBook(filePath).get();
        assertEquals(original, new DataBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.add(IPHONE_JSON_TEST2);
        original.remove(IPHONEONE);
        jsonPhoneBookStorage.savePhoneBook(original, filePath);
        readBack = jsonPhoneBookStorage.readPhoneBook(filePath).get();
        assertEquals(original, new DataBook<Phone>(readBack));

        // Save and read without specifying file path
        original.add(IPHONE_JSON_TEST3);
        jsonPhoneBookStorage.savePhoneBook(original); // file path not specified
        readBack = jsonPhoneBookStorage.readPhoneBook().get(); // file path not specified
        assertEquals(original, new DataBook(readBack));

    }

    @Test
    public void savePhoneBook_nullPhoneBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePhoneBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code orderBook} at the specified {@code filePath}.
     */
    private void savePhoneBook(ReadOnlyDataBook phoneBook, String filePath) {
        try {
            new JsonPhoneBookStorage(Paths.get(filePath))
                    .savePhoneBook(phoneBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void savePhoneBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePhoneBook(new DataBook(), null));
    }
}