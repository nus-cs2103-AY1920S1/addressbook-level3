package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_1;
import static seedu.address.testutil.TypicalBooks.BOOK_2;
import static seedu.address.testutil.TypicalBooks.BOOK_3;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalogue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Catalogue;
import seedu.address.model.ReadOnlyCatalogue;

public class JsonCatalogueStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readCatalogue_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCatalogue(null));
    }

    private java.util.Optional<ReadOnlyCatalogue> readCatalogue(String filePath) throws Exception {
        return new JsonCatalogueStorage(Paths.get(filePath)).readCatalogue(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCatalogue("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readCatalogue("notJsonFormatCatalogue.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCatalogue("invalidBookCatalogue.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCatalogue("invalidAndValidBookCatalogue.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        Catalogue original = getTypicalCatalogue();
        JsonCatalogueStorage jsonAddressBookStorage = new JsonCatalogueStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        ReadOnlyCatalogue readBack = jsonAddressBookStorage.readCatalogue(filePath).get();
        assertEquals(original, new Catalogue(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addBook(BOOK_1);
        original.removeBook(BOOK_2);
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        readBack = jsonAddressBookStorage.readCatalogue(filePath).get();
        assertEquals(original, new Catalogue(readBack));

        // Save and read without specifying file path
        original.addBook(BOOK_3);
        jsonAddressBookStorage.saveAddressBook(original); // file path not specified
        readBack = jsonAddressBookStorage.readCatalogue().get(); // file path not specified
        assertEquals(original, new Catalogue(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyCatalogue addressBook, String filePath) {
        try {
            new JsonCatalogueStorage(Paths.get(filePath))
                    .saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new Catalogue(), null));
    }
}
