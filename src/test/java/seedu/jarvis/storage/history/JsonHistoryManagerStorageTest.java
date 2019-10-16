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

    private void saveAddressBook(HistoryManager historyManager, String filePath) {
        try {
            new JsonHistoryManagerStorage(Paths.get(filePath))
                    .saveHistoryManager(historyManager, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    private Optional<HistoryManager> readHistoryManager(String filePath) throws Exception {
        return new JsonHistoryManagerStorage(Paths.get(filePath)).readHistoryManager(
                addToTestDataPathIfNotNull(filePath));
    }

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


}
