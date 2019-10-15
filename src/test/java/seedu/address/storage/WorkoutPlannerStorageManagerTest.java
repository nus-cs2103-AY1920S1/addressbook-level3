package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalExercises.getTypicalDukeCooks;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.ReadOnlyWorkoutPlanner;
import seedu.address.model.WorkoutPlanner;
import seedu.address.model.WorkoutPlannerUserPrefs;

public class WorkoutPlannerStorageManagerTest {

    @TempDir
    public Path testFolder;

    private WorkoutPlannerStorageManager workoutPlannerStorageManager;

    @BeforeEach
    public void setUp() {
        JsonWorkoutPlannerStorage dukeCooksStorage = new JsonWorkoutPlannerStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        workoutPlannerStorageManager = new WorkoutPlannerStorageManager(dukeCooksStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        WorkoutPlannerUserPrefs original = new WorkoutPlannerUserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        workoutPlannerStorageManager.saveUserPrefs(original);
        WorkoutPlannerUserPrefs retrieved = workoutPlannerStorageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void dukeCooksReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonDukeCooksStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonDukeCooksStorageTest} class.
         */
        WorkoutPlanner original = getTypicalDukeCooks();
        workoutPlannerStorageManager.saveDukeCooks(original);
        ReadOnlyWorkoutPlanner retrieved = workoutPlannerStorageManager.readDukeCooks().get();
        assertEquals(original, new WorkoutPlanner(retrieved));
    }

    @Test
    public void getDukeCooksFilePath() {
        assertNotNull(workoutPlannerStorageManager.getDukeCooksFilePath());
    }

}
