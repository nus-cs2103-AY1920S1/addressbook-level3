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
import dukecooks.model.workout.exercise.ReadOnlyExerciseCatalogue;
import dukecooks.model.workout.exercise.ExerciseCatalogue;
import dukecooks.testutil.Assert;

public class JsonExerciseCatalogueStorageStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonWorkoutPlannerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readWorkoutPlanner_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readWorkoutPlanner(null));
    }

    private java.util.Optional<ReadOnlyExerciseCatalogue> readWorkoutPlanner(String filePath) throws Exception {
        return new JsonExerciseCatalogueStorage(Paths.get(filePath))
                .readExerciseCatalogue(addToTestDataPathIfNotNull(filePath));
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
        ExerciseCatalogue original = getTypicalWorkoutPlanner();
        JsonExerciseCatalogueStorage jsonDukeCooksStorage = new JsonExerciseCatalogueStorage(filePath);

        // Save in new file and read back
        jsonDukeCooksStorage.saveExerciseCatalogue(original, filePath);
        ReadOnlyExerciseCatalogue readBack = jsonDukeCooksStorage.readExerciseCatalogue(filePath).get();
        assertEquals(original, new ExerciseCatalogue(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addExercise(HOON);
        original.removeExercise(ABS_ROLLOUT);
        jsonDukeCooksStorage.saveExerciseCatalogue(original, filePath);
        readBack = jsonDukeCooksStorage.readExerciseCatalogue(filePath).get();
        assertEquals(original, new ExerciseCatalogue(readBack));

        // Save and read without specifying file path
        original.addExercise(IDA);
        jsonDukeCooksStorage.saveExerciseCatalogue(original); // file path not specified
        readBack = jsonDukeCooksStorage.readExerciseCatalogue().get(); // file path not specified
        assertEquals(original, new ExerciseCatalogue(readBack));

    }

    @Test
    public void saveDukeCooks_nullDukeCooks_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveDukeCooks(null, "SomeFile.json"));
    }

    /**
     * Saves {@code dukeCooks} at the specified {@code filePath}.
     */
    private void saveDukeCooks(ReadOnlyExerciseCatalogue dukeCooks, String filePath) {
        try {
            new JsonExerciseCatalogueStorage(Paths.get(filePath))
                    .saveExerciseCatalogue(dukeCooks, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveDukeCooks_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveDukeCooks(new ExerciseCatalogue(), null));
    }
}
