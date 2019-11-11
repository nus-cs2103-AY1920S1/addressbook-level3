package seedu.weme.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.weme.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.commons.exceptions.DataConversionException;
import seedu.weme.model.ReadOnlyWeme;
import seedu.weme.model.Weme;

public class JsonWemeStorageTest extends ApplicationTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonWemeStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readWeme_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readWeme(null));
    }

    private java.util.Optional<ReadOnlyWeme> readWeme(String filePath) throws Exception {
        return new JsonWemeStorage(Paths.get(filePath)).readWeme(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readWeme("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, (
            ) -> readWeme("notJsonFormatWeme"));
    }

    @Test
    public void readWeme_invalidMemeWeme_throwDataConversionException() {
        assertThrows(DataConversionException.class, (
            ) -> readWeme("invalidMemeWeme"));
    }

    @Test
    public void readWeme_invalidAndValidMemeWeme_throwDataConversionException() {
        assertThrows(DataConversionException.class, (
            ) -> readWeme("invalidAndValidMemeWeme"));
    }


    @Test
    public void saveWeme_nullWeme_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveWeme(null, "SomeFile.json"));
    }

    /**
     * Saves {@code weme} at the specified {@code filePath}.
     */
    private void saveWeme(ReadOnlyWeme weme, String filePath) {
        try {
            new JsonWemeStorage(Paths.get(filePath))
                    .saveWeme(weme, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveWeme_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveWeme(new Weme(), null));
    }
}
