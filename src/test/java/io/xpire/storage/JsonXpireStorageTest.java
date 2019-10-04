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
import io.xpire.model.ReadOnlyXpire;
import io.xpire.model.Xpire;

public class JsonXpireStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonXpireStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readExpiryDateTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readExpiryDateTracker(null));
    }

    private java.util.Optional<ReadOnlyXpire> readExpiryDateTracker(String filePath) throws Exception {
        return new JsonXpireStorage(Paths.get(filePath))
                .readXpire(addToTestDataPathIfNotNull(filePath));
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
                readExpiryDateTracker("notJsonFormatXpire.json"));
    }

    @Test
    public void readExpiryDateTracker_invalidItemExpiryDateTracker_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
                readExpiryDateTracker("invalidItemXpire.json"));
    }

    @Test
    public void readExpiryDateTracker_invalidAndValidItemExpiryDateTracker_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
                readExpiryDateTracker("invalidAndValidItemXpire.json"));
    }

    @Test
    public void readAndSaveExpiryDateTracker_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempXpire.json");
        Xpire original = getTypicalExpiryDateTracker();
        JsonXpireStorage jsonExpiryDateTrackerStorage = new JsonXpireStorage(filePath);

        // Save in new file and read back
        jsonExpiryDateTrackerStorage.saveXpire(original, filePath);
        ReadOnlyXpire readBack = jsonExpiryDateTrackerStorage.readXpire(filePath).get();
        assertEquals(original, new Xpire(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addItem(KIWI);
        jsonExpiryDateTrackerStorage.saveXpire(original, filePath);
        readBack = jsonExpiryDateTrackerStorage.readXpire(filePath).get();
        assertEquals(original, new Xpire(readBack));

        // Save and read without specifying file path
        jsonExpiryDateTrackerStorage.saveXpire(original); // file path not specified
        readBack = jsonExpiryDateTrackerStorage.readXpire().get(); // file path not specified
        assertEquals(original, new Xpire(readBack));

    }

    @Test
    public void saveExpiryDateTracker_nullExpiryDateTracker_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveExpiryDateTracker(null, "SomeFile.json"));
    }

    /**
     * Saves {@code xpire} at the specified {@code filePath}.
     */
    private void saveExpiryDateTracker(ReadOnlyXpire addressBook, String filePath) {
        try {
            new JsonXpireStorage(Paths.get(filePath))
                    .saveXpire(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveExpiryDateTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveExpiryDateTracker(new Xpire(), null));
    }
}
