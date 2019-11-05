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
import seedu.address.model.LoanRecords;
import seedu.address.model.ReadOnlyCatalog;

public class JsonCatalogStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonCatalogStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readCatalog_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCatalog(null));
    }

    private java.util.Optional<ReadOnlyCatalog> readCatalog(String filePath) throws Exception {
        return new JsonCatalogStorage(Paths.get(filePath)).readCatalog(addToTestDataPathIfNotNull(filePath),
                new LoanRecords());
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
    public void readCatalog_invalidPersonCatalog_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCatalog("invalidBookCatalog.json"));
    }

    @Test
    public void readCatalog_invalidAndValidPersonCatalog_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCatalog("invalidAndValidBookCatalog.json"));
    }

    @Test
    public void readAndSaveCatalog_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempCatalog.json");
        Catalog original = getTypicalCatalog();
        JsonCatalogStorage jsonCatalogStorage = new JsonCatalogStorage(filePath);

        // Save in new file and read back
        jsonCatalogStorage.saveCatalog(original, filePath);
        ReadOnlyCatalog readBack = jsonCatalogStorage.readCatalog(filePath, new LoanRecords()).get();
        assertEquals(original, new Catalog(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addBook(BOOK_5);
        original.removeBook(BOOK_2);
        jsonCatalogStorage.saveCatalog(original, filePath);
        readBack = jsonCatalogStorage.readCatalog(filePath, new LoanRecords()).get();
        assertEquals(original, new Catalog(readBack));

        // Save and read without specifying file path
        original.addBook(BOOK_6);
        jsonCatalogStorage.saveCatalog(original); // file path not specified
        readBack = jsonCatalogStorage.readCatalog(new LoanRecords()).get(); // file path not specified
        assertEquals(original, new Catalog(readBack));
    }

    @Test
    public void saveCatalog_nullCatalog_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCatalog(null, "SomeFile.json"));
    }

    /**
     * Saves {@code catalog} at the specified {@code filePath}.
     */
    private void saveCatalog(ReadOnlyCatalog catalog, String filePath) {
        try {
            new JsonCatalogStorage(Paths.get(filePath))
                    .saveCatalog(catalog, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCatalog_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCatalog(new Catalog(), null));
    }
}
