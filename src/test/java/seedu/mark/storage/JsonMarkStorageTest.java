package seedu.mark.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalBookmarks.ALICE;
import static seedu.mark.testutil.TypicalBookmarks.HOON;
import static seedu.mark.testutil.TypicalBookmarks.IDA;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.mark.commons.exceptions.DataConversionException;
import seedu.mark.model.Mark;
import seedu.mark.model.ReadOnlyMark;
import seedu.mark.model.bookmark.Folder;

public class JsonMarkStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonMarkStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readMark_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readMark(null));
    }

    private java.util.Optional<ReadOnlyMark> readMark(String filePath) throws Exception {
        return new JsonMarkStorage(Paths.get(filePath)).readMark(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readMark("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readMark("notJsonFormatMark.json"));
    }

    @Test
    public void readMark_invalidBookmarkMark_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readMark("invalidBookmarkMark.json"));
    }

    @Test
    public void readMark_invalidAndValidBookmarkMark_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readMark("invalidAndValidBookmarkMark.json"));
    }

    @Test
    public void readAndSaveMark_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempMark.json");
        Mark original = getTypicalMark();
        JsonMarkStorage jsonMarkStorage = new JsonMarkStorage(filePath);

        // Save in new file and read back
        jsonMarkStorage.saveMark(original, filePath);
        ReadOnlyMark readBack = jsonMarkStorage.readMark(filePath).get();
        assertEquals(original, new Mark(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addBookmark(HOON);
        original.removeBookmark(ALICE);
        original.addFolder(new Folder("newfolder"), Folder.ROOT_FOLDER);
        jsonMarkStorage.saveMark(original, filePath);
        readBack = jsonMarkStorage.readMark(filePath).get();
        assertEquals(original, new Mark(readBack));

        // Save and read without specifying file path
        original.addBookmark(IDA);
        jsonMarkStorage.saveMark(original); // file path not specified
        readBack = jsonMarkStorage.readMark().get(); // file path not specified
        assertEquals(original, new Mark(readBack));

    }

    @Test
    public void saveMark_nullMark_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveMark(null, "SomeFile.json"));
    }

    /**
     * Saves {@code mark} at the specified {@code filePath}.
     */
    private void saveMark(ReadOnlyMark mark, String filePath) {
        try {
            new JsonMarkStorage(Paths.get(filePath))
                    .saveMark(mark, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveMark_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveMark(new Mark(), null));
    }
}
