//@@author e0031374
package tagline.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static tagline.testutil.Assert.assertThrows;
import static tagline.testutil.TypicalGroups.ASGARDIAN;
import static tagline.testutil.TypicalGroups.CHILDREN;
import static tagline.testutil.TypicalGroups.MYSTIC_ARTS;
import static tagline.testutil.TypicalGroups.getTypicalGroupBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import tagline.commons.exceptions.DataConversionException;
import tagline.model.group.GroupBook;
import tagline.model.group.ReadOnlyGroupBook;
import tagline.storage.group.JsonGroupBookStorage;

public class JsonGroupBookStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonGroupBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readGroupBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readGroupBook(null));
    }

    private java.util.Optional<ReadOnlyGroupBook> readGroupBook(String filePath) throws Exception {
        return new JsonGroupBookStorage(Paths.get(filePath)).readGroupBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readGroupBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readGroupBook("notJsonFormatGroupBook.json"));
    }

    @Test
    public void readGroupBook_invalidGroupGroupBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readGroupBook("invalidGroupGroupBook.json"));
    }

    @Test
    public void readGroupBook_invalidAndValidGroupGroupBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readGroupBook("invalidAndValidGroupGroupBook.json"));
    }

    @Test
    public void readAndSaveGroupBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempGroupBook.json");
        Path filePath2 = TEST_DATA_FOLDER.resolve("TempGroupBook2.json");
        GroupBook original = getTypicalGroupBook();
        JsonGroupBookStorage jsonGroupBookStorage = new JsonGroupBookStorage(filePath);

        // Save in new file and read back
        jsonGroupBookStorage.saveGroupBook(original, filePath);
        ReadOnlyGroupBook readBack = jsonGroupBookStorage.readGroupBook(filePath).get();
        assertEquals(original, new GroupBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addGroup(ASGARDIAN);
        original.removeGroup(CHILDREN);
        jsonGroupBookStorage.saveGroupBook(original, filePath);
        readBack = jsonGroupBookStorage.readGroupBook(filePath).get();
        assertEquals(original, new GroupBook(readBack));

        // Save and read without specifying file path
        original.addGroup(MYSTIC_ARTS);
        jsonGroupBookStorage.saveGroupBook(original); // file path not specified
        readBack = jsonGroupBookStorage.readGroupBook().get(); // file path not specified
        assertEquals(original, new GroupBook(readBack));

    }

    @Test
    public void saveGroupBook_nullGroupBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveGroupBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code groupBook} at the specified {@code filePath}.
     */
    private void saveGroupBook(ReadOnlyGroupBook groupBook, String filePath) {
        try {
            new JsonGroupBookStorage(Paths.get(filePath))
                    .saveGroupBook(groupBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveGroupBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveGroupBook(new GroupBook(), null));
    }
}
