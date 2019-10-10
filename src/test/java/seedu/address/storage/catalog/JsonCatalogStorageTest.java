package seedu.address.storage.catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_2;
import static seedu.address.testutil.TypicalBooks.BOOK_5;
import static seedu.address.testutil.TypicalBooks.BOOK_6;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalog;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Catalog;
import seedu.address.model.ReadOnlyCatalog;
import seedu.address.storage.catalog.JsonCatalogStorage;

public class JsonCatalogStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonCatalogStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readCatalog_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCatalog(null));
    }

    private java.util.Optional<ReadOnlyCatalog> readCatalog(String filePath) throws Exception {
        return new JsonCatalogStorage(Paths.get(filePath)).readCatalog(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCatalog("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readCatalog("notJsonFormatCatalog.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCatalog("invalidBookCatalog.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCatalog("invalidAndValidBookCatalog.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempCatalog.json");
        Catalog original = getTypicalCatalog();
        JsonCatalogStorage jsonAddressBookStorage = new JsonCatalogStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveCatalog(original, filePath);
        ReadOnlyCatalog readBack = jsonAddressBookStorage.readCatalog(filePath).get();
        assertEquals(original, new Catalog(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addBook(BOOK_5);
        original.removeBook(BOOK_2);
        jsonAddressBookStorage.saveCatalog(original, filePath);
        readBack = jsonAddressBookStorage.readCatalog(filePath).get();
        assertEquals(original, new Catalog(readBack));

        // Save and read without specifying file path
        original.addBook(BOOK_6);
        jsonAddressBookStorage.saveCatalog(original); // file path not specified
        readBack = jsonAddressBookStorage.readCatalog().get(); // file path not specified
        assertEquals(original, new Catalog(readBack));
    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyCatalog addressBook, String filePath) {
        try {
            new JsonCatalogStorage(Paths.get(filePath))
                    .saveCatalog(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new Catalog(), null));
    }
}
