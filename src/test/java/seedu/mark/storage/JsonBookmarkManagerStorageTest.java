package seedu.mark.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalPersons.ALICE;
import static seedu.mark.testutil.TypicalPersons.HOON;
import static seedu.mark.testutil.TypicalPersons.IDA;
import static seedu.mark.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.mark.commons.exceptions.DataConversionException;
import seedu.mark.model.BookmarkManager;
import seedu.mark.model.ReadOnlyBookmarkManager;

public class JsonBookmarkManagerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonBookmarkManagerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyBookmarkManager> readAddressBook(String filePath) throws Exception {
        return new JsonBookmarkManagerStorage(Paths.get(filePath)).readBookmarkManager(addToTestDataPathIfNotNull(filePath));
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
        BookmarkManager original = getTypicalAddressBook();
        JsonBookmarkManagerStorage jsonAddressBookStorage = new JsonBookmarkManagerStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveBookmarkManager(original, filePath);
        ReadOnlyBookmarkManager readBack = jsonAddressBookStorage.readBookmarkManager(filePath).get();
        assertEquals(original, new BookmarkManager(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addBookmark(HOON);
        original.removeBookmark(ALICE);
        jsonAddressBookStorage.saveBookmarkManager(original, filePath);
        readBack = jsonAddressBookStorage.readBookmarkManager(filePath).get();
        assertEquals(original, new BookmarkManager(readBack));

        // Save and read without specifying file path
        original.addBookmark(IDA);
        jsonAddressBookStorage.saveBookmarkManager(original); // file path not specified
        readBack = jsonAddressBookStorage.readBookmarkManager().get(); // file path not specified
        assertEquals(original, new BookmarkManager(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyBookmarkManager addressBook, String filePath) {
        try {
            new JsonBookmarkManagerStorage(Paths.get(filePath))
                    .saveBookmarkManager(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new BookmarkManager(), null));
    }
}
