package io.xpire.storage;

import static io.xpire.testutil.Assert.assertThrows;
import static io.xpire.testutil.TypicalItems.KIWI;
import static io.xpire.testutil.TypicalItems.getTypicalExpiryDateTracker;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import io.xpire.commons.exceptions.DataConversionException;
import io.xpire.model.ExpiryDateTracker;
import io.xpire.model.ReadOnlyExpiryDateTracker;

public class JsonExpiryDateTrackerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readExpiryDateTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readExpiryDateTracker(null));
    }

    private java.util.Optional<ReadOnlyExpiryDateTracker> readExpiryDateTracker(String filePath) throws Exception {
        return new JsonExpiryDateTrackerStorage(Paths.get(filePath))
                .readExpiryDateTracker(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readExpiryDateTracker("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () ->
                readExpiryDateTracker("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readExpiryDateTracker_invalidItemExpiryDateTracker_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
                readExpiryDateTracker("invalidItemExpiryDateTracker.json"));
    }

    @Test
    public void readExpiryDateTracker_invalidAndValidItemExpiryDateTracker_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
                readExpiryDateTracker("invalidAndValidItemExpiryDateTracker.json"));
    }

    @Test
    public void readAndSaveExpiryDateTracker_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        ExpiryDateTracker original = getTypicalExpiryDateTracker();
        JsonExpiryDateTrackerStorage jsonExpiryDateTrackerStorage = new JsonExpiryDateTrackerStorage(filePath);

        // Save in new file and read back
        jsonExpiryDateTrackerStorage.saveExpiryDateTracker(original, filePath);
        ReadOnlyExpiryDateTracker readBack = jsonExpiryDateTrackerStorage.readExpiryDateTracker(filePath).get();
        assertEquals(original, new ExpiryDateTracker(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addItem(KIWI);
        jsonExpiryDateTrackerStorage.saveExpiryDateTracker(original, filePath);
        readBack = jsonExpiryDateTrackerStorage.readExpiryDateTracker(filePath).get();
        assertEquals(original, new ExpiryDateTracker(readBack));

        // Save and read without specifying file path
        jsonExpiryDateTrackerStorage.saveExpiryDateTracker(original); // file path not specified
        readBack = jsonExpiryDateTrackerStorage.readExpiryDateTracker().get(); // file path not specified
        assertEquals(original, new ExpiryDateTracker(readBack));

    }

    @Test
    public void saveExpiryDateTracker_nullExpiryDateTracker_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveExpiryDateTracker(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveExpiryDateTracker(ReadOnlyExpiryDateTracker addressBook, String filePath) {
        try {
            new JsonExpiryDateTrackerStorage(Paths.get(filePath))
                    .saveExpiryDateTracker(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveExpiryDateTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveExpiryDateTracker(new ExpiryDateTracker(), null));
    }
}
