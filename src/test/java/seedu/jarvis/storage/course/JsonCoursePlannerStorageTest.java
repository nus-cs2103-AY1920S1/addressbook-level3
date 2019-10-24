package seedu.jarvis.storage.course;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.model.course.CoursePlanner;

/**
 * Tests the behaviour of {@code JsonCoursePlannerStorage}.
 */
public class JsonCoursePlannerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonCoursePlannerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readCoursePlanner_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCoursePlanner(null));
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCoursePlanner("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readCoursePlanner("notJsonFormatCoursePlanner.json"));
    }

    @Test
    public void saveCoursePlanner_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCoursePlanner(new CoursePlanner(), null));
    }

    /**
     * Saves {@code coursePlanner} at the specified {@code filePath}.
     */
    private void saveCoursePlanner(CoursePlanner coursePlanner, String filePath) {
        try {
            new JsonCoursePlannerStorage(Paths.get(filePath))
                    .saveCoursePlanner(coursePlanner, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    /**
     * Gets an {@code Optional} of {@code CoursePlanner} from reading the file at the given path.
     */
    private Optional<CoursePlanner> readCoursePlanner(String filePath) throws Exception {
        return new JsonCoursePlannerStorage(Paths.get(filePath)).readCoursePlanner(
                addToTestDataPathIfNotNull(filePath));
    }

    /**
     * Gets the {@code Path} from a given {@code String} path.
     */
    private Path addToTestDataPathIfNotNull(String filePath) {
        return filePath != null ? TEST_DATA_FOLDER.resolve(filePath) : null;
    }

}
