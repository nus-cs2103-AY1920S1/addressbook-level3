package seedu.jarvis.storage.finance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.model.finance.FinanceTracker;

/**
 * Tests the behaviour of {@code JsonCourseFinanceTracker}.
 */
public class JsonFinanceTrackerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonFinanceTrackerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readFinanceTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readFinanceTracker(null));
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readFinanceTracker("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readFinanceTracker("notJsonFormatFinanceTracker.json"));
    }

    @Test
    public void saveCoursePlanner_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveFinanceTracker(new FinanceTracker(), null));
    }

    /**
     * Saves {@code financeTracker} at the specified {@code filePath}.
     */
    private void saveFinanceTracker(FinanceTracker financeTracker, String filePath) {
        try {
            new JsonFinanceTrackerStorage(Paths.get(filePath))
                    .saveFinanceTracker(financeTracker, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    /**
     * Gets an {@code Optional} of {@code FinanceTracker} from reading the file at the given path.
     */
    private Optional<FinanceTracker> readFinanceTracker(String filePath) throws Exception {
        return new JsonFinanceTrackerStorage(Paths.get(filePath)).readFinanceTracker(
                addToTestDataPathIfNotNull(filePath));
    }

    /**
     * Gets the {@code Path} from a given {@code String} path.
     */
    private Path addToTestDataPathIfNotNull(String filePath) {
        return filePath != null ? TEST_DATA_FOLDER.resolve(filePath) : null;
    }


}
