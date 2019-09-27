package seedu.mark.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalBookmarks.ALICE;
import static seedu.mark.testutil.TypicalBookmarks.HOON;
import static seedu.mark.testutil.TypicalBookmarks.IDA;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalBookmarkManager;

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
    public void readBookmarkManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readBookmarkManager(null));
    }

    private java.util.Optional<ReadOnlyBookmarkManager> readBookmarkManager(String filePath) throws Exception {
        return new JsonBookmarkManagerStorage(Paths.get(filePath)).readBookmarkManager(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readBookmarkManager("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readBookmarkManager("notJsonFormatBookmarkManager.json"));
    }

    @Test
    public void readBookmarkManager_invalidBookmarkBookmarkManager_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readBookmarkManager("invalidBookmarkBookmarkManager.json"));
    }

    @Test
    public void readBookmarkManager_invalidAndValidBookmarkBookmarkManager_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readBookmarkManager("invalidAndValidBookmarkBookmarkManager.json"));
    }

    @Test
    public void readAndSaveBookmarkManager_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempBookmarkManager.json");
        BookmarkManager original = getTypicalBookmarkManager();
        JsonBookmarkManagerStorage jsonBookmarkManagerStorage = new JsonBookmarkManagerStorage(filePath);

        // Save in new file and read back
        jsonBookmarkManagerStorage.saveBookmarkManager(original, filePath);
        ReadOnlyBookmarkManager readBack = jsonBookmarkManagerStorage.readBookmarkManager(filePath).get();
        assertEquals(original, new BookmarkManager(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addBookmark(HOON);
        original.removeBookmark(ALICE);
        jsonBookmarkManagerStorage.saveBookmarkManager(original, filePath);
        readBack = jsonBookmarkManagerStorage.readBookmarkManager(filePath).get();
        assertEquals(original, new BookmarkManager(readBack));

        // Save and read without specifying file path
        original.addBookmark(IDA);
        jsonBookmarkManagerStorage.saveBookmarkManager(original); // file path not specified
        readBack = jsonBookmarkManagerStorage.readBookmarkManager().get(); // file path not specified
        assertEquals(original, new BookmarkManager(readBack));

    }

    @Test
    public void saveBookmarkManager_nullBookmarkManager_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveBookmarkManager(null, "SomeFile.json"));
    }

    /**
     * Saves {@code bookmarkManager} at the specified {@code filePath}.
     */
    private void saveBookmarkManager(ReadOnlyBookmarkManager bookmarkManager, String filePath) {
        try {
            new JsonBookmarkManagerStorage(Paths.get(filePath))
                    .saveBookmarkManager(bookmarkManager, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveBookmarkManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveBookmarkManager(new BookmarkManager(), null));
    }
}
