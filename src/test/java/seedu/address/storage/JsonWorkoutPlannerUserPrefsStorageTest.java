package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.WorkoutPlannerUserPrefs;

public class JsonWorkoutPlannerUserPrefsStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonUserPrefsStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readUserPrefs_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readUserPrefs(null));
    }

    private Optional<WorkoutPlannerUserPrefs> readUserPrefs(String userPrefsFileInTestDataFolder)
            throws DataConversionException {
        Path prefsFilePath = addToTestDataPathIfNotNull(userPrefsFileInTestDataFolder);
        return new JsonUserPrefsStorage(prefsFilePath).readUserPrefs(prefsFilePath);
    }

    @Test
    public void readUserPrefs_missingFile_emptyResult() throws DataConversionException {
        assertFalse(readUserPrefs("NonExistentFile.json").isPresent());
    }

    @Test
    public void readUserPrefs_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readUserPrefs("NotJsonFormatUserPrefs.json"));
    }

    private Path addToTestDataPathIfNotNull(String userPrefsFileInTestDataFolder) {
        return userPrefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(userPrefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void readUserPrefs_fileInOrder_successfullyRead() throws DataConversionException {
        WorkoutPlannerUserPrefs expected = getTypicalUserPrefs();
        WorkoutPlannerUserPrefs actual = readUserPrefs("TypicalUserPref.json").get();
        assertEquals(expected, actual);
    }

    @Test
    public void readUserPrefs_valuesMissingFromFile_defaultValuesUsed() throws DataConversionException {
        WorkoutPlannerUserPrefs actual = readUserPrefs("EmptyUserPrefs.json").get();
        assertEquals(new WorkoutPlannerUserPrefs(), actual);
    }

    @Test
    public void readUserPrefs_extraValuesInFile_extraValuesIgnored() throws DataConversionException {
        WorkoutPlannerUserPrefs expected = getTypicalUserPrefs();
        WorkoutPlannerUserPrefs actual = readUserPrefs("ExtraValuesUserPref.json").get();

        assertEquals(expected, actual);
    }

    private WorkoutPlannerUserPrefs getTypicalUserPrefs() {
        WorkoutPlannerUserPrefs workoutPlannerUserPrefs = new WorkoutPlannerUserPrefs();
        workoutPlannerUserPrefs.setGuiSettings(new GuiSettings(1000, 500, 300, 100));
        workoutPlannerUserPrefs.setExercisesFilePath(Paths.get("exercises.json"));
        return workoutPlannerUserPrefs;
    }

    @Test
    public void savePrefs_nullPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveUserPrefs(null, "SomeFile.json"));
    }

    @Test
    public void saveUserPrefs_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveUserPrefs(new WorkoutPlannerUserPrefs(), null));
    }

    /**
     * Saves {@code userPrefs} at the specified {@code prefsFileInTestDataFolder} filepath.
     */
    private void saveUserPrefs(WorkoutPlannerUserPrefs workoutPlannerUserPrefs, String prefsFileInTestDataFolder) {
        try {
            new JsonUserPrefsStorage(addToTestDataPathIfNotNull(prefsFileInTestDataFolder))
                    .saveUserPrefs(workoutPlannerUserPrefs);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file", ioe);
        }
    }

    @Test
    public void saveUserPrefs_allInOrder_success() throws DataConversionException, IOException {

        WorkoutPlannerUserPrefs original = new WorkoutPlannerUserPrefs();
        original.setGuiSettings(new GuiSettings(1200, 200, 0, 2));

        Path pefsFilePath = testFolder.resolve("TempPrefs.json");
        JsonUserPrefsStorage jsonUserPrefsStorage = new JsonUserPrefsStorage(pefsFilePath);

        //Try writing when the file doesn't exist
        jsonUserPrefsStorage.saveUserPrefs(original);
        WorkoutPlannerUserPrefs readBack = jsonUserPrefsStorage.readUserPrefs().get();
        assertEquals(original, readBack);

        //Try saving when the file exists
        original.setGuiSettings(new GuiSettings(5, 5, 5, 5));
        jsonUserPrefsStorage.saveUserPrefs(original);
        readBack = jsonUserPrefsStorage.readUserPrefs().get();
        assertEquals(original, readBack);
    }

}
