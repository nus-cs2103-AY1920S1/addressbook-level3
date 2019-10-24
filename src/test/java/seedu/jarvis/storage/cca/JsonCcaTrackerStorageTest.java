package seedu.jarvis.storage.cca;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.model.cca.CcaTracker;

/**
 * Tests the behaviour of {@code JsonCcaTrackerStorage}.
 */
public class JsonCcaTrackerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonCcaTrackerStorageTest");

    @TempDir
    public Path testFolder;

    /**
     * Saves {@code ccaTracker} at the specified {@code filePath}.
     */
    private void saveCcaTracker(CcaTracker ccaTracker, String filePath) {
        try {
            new JsonCcaTrackerStorage(Paths.get(filePath)).saveCcaTracker(ccaTracker,
                    addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    /**
     * Gets an {@code Optional} of {@code CcaTracker} from reading the file at the given path.
     */
    private Optional<CcaTracker> readCcaTracker(String filePath) throws Exception {
        return new JsonCcaTrackerStorage(Paths.get(filePath)).readCcaTracker(addToTestDataPathIfNotNull(filePath));
    }

    /**
     * Gets the {@code Path} from a given {@code String} path.
     */
    private Path addToTestDataPathIfNotNull(String filePath) {
        return filePath != null ? TEST_DATA_FOLDER.resolve(filePath) : null;
    }

    @Test
    public void readCcaTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCcaTracker(null));
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCcaTracker("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readCcaTracker("notJsonFormatCcaTracker.json"));
    }

    @Test
    public void saveCcaTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCcaTracker(new CcaTracker(), null));
    }

}
