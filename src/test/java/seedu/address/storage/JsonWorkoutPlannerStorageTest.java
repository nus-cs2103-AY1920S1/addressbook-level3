package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalExercises.ABS_ROLLOUT;
import static seedu.address.testutil.TypicalExercises.HOON;
import static seedu.address.testutil.TypicalExercises.IDA;
import static seedu.address.testutil.TypicalExercises.getTypicalDukeCooks;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyWorkoutPlanner;
import seedu.address.model.WorkoutPlanner;

public class JsonWorkoutPlannerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonDukeCooksStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readDukeCooks_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readDukeCooks(null));
    }

    private java.util.Optional<ReadOnlyWorkoutPlanner> readDukeCooks(String filePath) throws Exception {
        return new JsonWorkoutPlannerStorage(Paths.get(filePath)).readDukeCooks(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readDukeCooks("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readDukeCooks("notJsonFormatDukeCooks.json"));
    }

    @Test
    public void readDukeCooks_invalidPersonDukeCooks_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readDukeCooks("invalidPersonDukeCooks.json"));
    }

    @Test
    public void readDukeCooks_invalidAndValidPersonDukeCooks_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readDukeCooks("invalidAndValidPersonDukeCooks.json"));
    }

    @Test
    public void readAndSaveDukeCooks_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempDukeCooks.json");
        WorkoutPlanner original = getTypicalDukeCooks();
        JsonWorkoutPlannerStorage jsonDukeCooksStorage = new JsonWorkoutPlannerStorage(filePath);

        // Save in new file and read back
        jsonDukeCooksStorage.saveDukeCooks(original, filePath);
        ReadOnlyWorkoutPlanner readBack = jsonDukeCooksStorage.readDukeCooks(filePath).get();
        assertEquals(original, new WorkoutPlanner(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addExercise(HOON);
        original.removePerson(ABS_ROLLOUT);
        jsonDukeCooksStorage.saveDukeCooks(original, filePath);
        readBack = jsonDukeCooksStorage.readDukeCooks(filePath).get();
        assertEquals(original, new WorkoutPlanner(readBack));

        // Save and read without specifying file path
        original.addExercise(IDA);
        jsonDukeCooksStorage.saveDukeCooks(original); // file path not specified
        readBack = jsonDukeCooksStorage.readDukeCooks().get(); // file path not specified
        assertEquals(original, new WorkoutPlanner(readBack));

    }

    @Test
    public void saveDukeCooks_nullDukeCooks_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveDukeCooks(null, "SomeFile.json"));
    }

    /**
     * Saves {@code dukeCooks} at the specified {@code filePath}.
     */
    private void saveDukeCooks(ReadOnlyWorkoutPlanner dukeCooks, String filePath) {
        try {
            new JsonWorkoutPlannerStorage(Paths.get(filePath))
                    .saveDukeCooks(dukeCooks, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveDukeCooks_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveDukeCooks(new WorkoutPlanner(), null));
    }
}
