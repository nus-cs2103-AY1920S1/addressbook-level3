package dukecooks.storage.workout.exercise;

import static dukecooks.testutil.exercise.TypicalExercises.ABS_ROLLOUT;
import static dukecooks.testutil.exercise.TypicalExercises.HOON;
import static dukecooks.testutil.exercise.TypicalExercises.IDA;
import static dukecooks.testutil.exercise.TypicalExercises.getTypicalWorkoutPlanner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import dukecooks.commons.exceptions.DataConversionException;
import dukecooks.model.workout.ReadOnlyWorkoutPlanner;
import dukecooks.model.workout.WorkoutPlanner;
import dukecooks.testutil.Assert;

public class JsonWorkoutPlannerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonWorkoutPlannerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readWorkoutPlanner_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readWorkoutPlanner(null));
    }

    private java.util.Optional<ReadOnlyWorkoutPlanner> readWorkoutPlanner(String filePath) throws Exception {
        return new JsonWorkoutPlannerStorage(Paths.get(filePath))
                .readWorkoutPlanner(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readWorkoutPlanner("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataConversionException.class, ()
            -> readWorkoutPlanner("notJsonFormatWorkoutPlanner.json"));
    }

    @Test
    public void readDukeCooks_invalidPersonDukeCooks_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, ()
            -> readWorkoutPlanner("invalidExerciseWorkoutPlanner.json"));
    }

    @Test
    public void readWorkoutPlanner_invalidAndValidExerciseWorkoutPlanner_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () ->
                readWorkoutPlanner("invalidAndValidExerciseWorkoutPlanner.json"));
    }

    @Test
    public void readAndSaveDukeCooks_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempDukeCooks.json");
        WorkoutPlanner original = getTypicalWorkoutPlanner();
        JsonWorkoutPlannerStorage jsonDukeCooksStorage = new JsonWorkoutPlannerStorage(filePath);

        // Save in new file and read back
        jsonDukeCooksStorage.saveWorkoutPlanner(original, filePath);
        ReadOnlyWorkoutPlanner readBack = jsonDukeCooksStorage.readWorkoutPlanner(filePath).get();
        assertEquals(original, new WorkoutPlanner(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addExercise(HOON);
        original.removeExercise(ABS_ROLLOUT);
        jsonDukeCooksStorage.saveWorkoutPlanner(original, filePath);
        readBack = jsonDukeCooksStorage.readWorkoutPlanner(filePath).get();
        assertEquals(original, new WorkoutPlanner(readBack));

        // Save and read without specifying file path
        original.addExercise(IDA);
        jsonDukeCooksStorage.saveWorkoutPlanner(original); // file path not specified
        readBack = jsonDukeCooksStorage.readWorkoutPlanner().get(); // file path not specified
        assertEquals(original, new WorkoutPlanner(readBack));

    }

    @Test
    public void saveDukeCooks_nullDukeCooks_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveDukeCooks(null, "SomeFile.json"));
    }

    /**
     * Saves {@code dukeCooks} at the specified {@code filePath}.
     */
    private void saveDukeCooks(ReadOnlyWorkoutPlanner dukeCooks, String filePath) {
        try {
            new JsonWorkoutPlannerStorage(Paths.get(filePath))
                    .saveWorkoutPlanner(dukeCooks, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveDukeCooks_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveDukeCooks(new WorkoutPlanner(), null));
    }
}
