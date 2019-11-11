package seedu.jarvis.storage.planner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.model.planner.Planner;

/**
 * Tests the behaviour of {@code JsonPlannerStorage}.
 */
public class JsonPlannerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonPlannerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readPlanner_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readPlanner(null));
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readPlanner("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readPlanner("notJsonFormatPlanner.json"));
    }

    @Test
    public void savePlanner_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePlanner(new Planner(), null));
    }

    /**
     * Saves {@code Planner} at the specified {@code filePath}.
     */
    private void savePlanner(Planner planner, String filePath) {
        try {
            new JsonPlannerStorage(Paths.get(filePath))
                    .savePlanner(planner, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    /**
     * Gets an {@code Optional} of {@code Planner} from reading the file at the given path.
     */
    private Optional<Planner> readPlanner(String filePath) throws Exception {
        return new JsonPlannerStorage(Paths.get(filePath)).readPlanner(
                addToTestDataPathIfNotNull(filePath));
    }

    /**
     * Gets the {@code Path} from a given {@code String} path.
     */
    private Path addToTestDataPathIfNotNull(String filePath) {
        return filePath != null ? TEST_DATA_FOLDER.resolve(filePath) : null;
    }

}
