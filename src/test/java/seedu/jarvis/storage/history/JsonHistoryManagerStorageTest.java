package seedu.jarvis.storage.history;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.model.history.HistoryManager;

/**
 * Tests the behaviour of {@code JsonHistoryManagerStorage}.
 */
public class JsonHistoryManagerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonHistoryManagerStorageTest");

    @TempDir
    public Path testFolder;

    /**
     * Saves {@code historyManager} at the specified {@code filePath}.
     */
    private void saveHistoryManager(HistoryManager historyManager, String filePath) {
        try {
            new JsonHistoryManagerStorage(Paths.get(filePath))
                    .saveHistoryManager(historyManager, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    /**
     * Gets an {@code Optional} of {@code HistoryManager} from reading the file at the given path.
     */
    private Optional<HistoryManager> readHistoryManager(String filePath) throws Exception {
        return new JsonHistoryManagerStorage(Paths.get(filePath)).readHistoryManager(
                addToTestDataPathIfNotNull(filePath));
    }

    /**
     * Gets the {@code Path} from a given {@code String} path.
     */
    private Path addToTestDataPathIfNotNull(String filePath) {
        return filePath != null ? TEST_DATA_FOLDER.resolve(filePath) : null;
    }

    @Test
    public void readHistoryManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readHistoryManager(null));
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readHistoryManager("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readHistoryManager("notJsonFormatHistoryManager.json"));
    }

    @Test
    public void saveHistoryManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveHistoryManager(new HistoryManager(), null));
    }

}
