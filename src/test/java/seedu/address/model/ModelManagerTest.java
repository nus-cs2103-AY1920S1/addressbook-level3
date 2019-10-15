package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXERCISE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalExercises.ABS_ROLLOUT;
import static seedu.address.testutil.TypicalExercises.BURPEES;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.exercise.NameContainsKeywordsPredicate;
import seedu.address.testutil.DukeCooksBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new WorkoutPlannerUserPrefs(), modelManager.getWorkoutPlannerUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new WorkoutPlanner(), new WorkoutPlanner(modelManager.getDukeCooks()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setWorkoutPlannerUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        WorkoutPlannerUserPrefs workoutPlannerUserPrefs = new WorkoutPlannerUserPrefs();
        workoutPlannerUserPrefs.setExercisesFilePath(Paths.get("address/book/file/path"));
        workoutPlannerUserPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setWorkoutPlannerUserPrefs(workoutPlannerUserPrefs);
        assertEquals(workoutPlannerUserPrefs, modelManager.getWorkoutPlannerUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        WorkoutPlannerUserPrefs oldWorkoutPlannerUserPrefs = new WorkoutPlannerUserPrefs(workoutPlannerUserPrefs);
        workoutPlannerUserPrefs.setExercisesFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldWorkoutPlannerUserPrefs, modelManager.getWorkoutPlannerUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setDukeCooksFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setDukeCooksFilePath(null));
    }

    @Test
    public void setDukeCooksFilePath_validPath_setsDukeCooksFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setDukeCooksFilePath(path);
        assertEquals(path, modelManager.getDukeCooksFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasExercise(null));
    }

    @Test
    public void hasPerson_personNotInDukeCooks_returnsFalse() {
        assertFalse(modelManager.hasExercise(ABS_ROLLOUT));
    }

    @Test
    public void hasPerson_personInDukeCooks_returnsTrue() {
        modelManager.addExercise(ABS_ROLLOUT);
        assertTrue(modelManager.hasExercise(ABS_ROLLOUT));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredExerciseList().remove(0));
    }

    @Test
    public void equals() {
        WorkoutPlanner dukeCooks = new DukeCooksBuilder().withExercise(ABS_ROLLOUT).withExercise(BURPEES).build();
        WorkoutPlanner differentDukeCooks = new WorkoutPlanner();
        WorkoutPlannerUserPrefs workoutPlannerUserPrefs = new WorkoutPlannerUserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(dukeCooks, workoutPlannerUserPrefs);
        ModelManager modelManagerCopy = new ModelManager(dukeCooks, workoutPlannerUserPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different dukeCooks -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentDukeCooks, workoutPlannerUserPrefs)));

        // different filteredList -> returns false
        String[] keywords = ABS_ROLLOUT.getExerciseName().exerciseName.split("\\s+");
        modelManager.updateFilteredExerciseList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(dukeCooks, workoutPlannerUserPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredExerciseList(PREDICATE_SHOW_ALL_EXERCISE);

        // different userPrefs -> returns false
        WorkoutPlannerUserPrefs differentWorkoutPlannerUserPrefs = new WorkoutPlannerUserPrefs();
        differentWorkoutPlannerUserPrefs.setExercisesFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(dukeCooks, differentWorkoutPlannerUserPrefs)));
    }
}
