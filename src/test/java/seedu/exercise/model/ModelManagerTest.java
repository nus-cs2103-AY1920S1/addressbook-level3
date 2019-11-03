package seedu.exercise.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.SWIM;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.WALK;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.exercise.commons.core.GuiSettings;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;
import seedu.exercise.testutil.builder.ExerciseBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new ReadOnlyResourceBook<Exercise>(),
            new ReadOnlyResourceBook<>(modelManager.getExerciseBookData()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setExerciseBookFilePath(Paths.get("exercise/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setExerciseBookFilePath(Paths.get("new/exercise/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
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
    public void setExerciseBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setExerciseBookFilePath(null));
    }

    @Test
    public void setExerciseBookFilePath_validPath_setsExerciseBookFilePath() {
        Path path = Paths.get("exercise/book/file/path");
        modelManager.setExerciseBookFilePath(path);
        assertEquals(path, modelManager.getExerciseBookFilePath());
    }

    @Test
    public void hasExercise_nullExercise_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasExercise(null));
    }

    @Test
    public void hasExercise_exerciseNotInExerciseBook_returnsFalse() {
        assertFalse(modelManager.hasExercise(WALK));
    }

    @Test
    public void hasExercise_exerciseInExerciseBook_returnsTrue() {
        modelManager.addExercise(WALK);
        assertTrue(modelManager.hasExercise(WALK));
    }

    @Test
    public void getFilteredExerciseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredExerciseList().remove(0));
    }

    @Test
    public void equals() {
        ReadOnlyResourceBook<Exercise> exerciseBook =
            new ExerciseBookBuilder().withExercise(WALK).withExercise(SWIM).build();
        ReadOnlyResourceBook<Regime> regimeBook = new ReadOnlyResourceBook<>();
        ReadOnlyResourceBook<Schedule> scheduleBook = new ReadOnlyResourceBook<>();
        ReadOnlyResourceBook<Exercise> databaseBook =
            new ExerciseBookBuilder().withExercise(WALK).withExercise(SWIM).build();
        ReadOnlyResourceBook<Exercise> differentExerciseBook = new ReadOnlyResourceBook<>();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(exerciseBook, regimeBook, databaseBook,
            scheduleBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(exerciseBook, regimeBook, databaseBook,
            scheduleBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different exerciseBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentExerciseBook, regimeBook,
            databaseBook, scheduleBook, userPrefs)));

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setExerciseBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(exerciseBook, regimeBook,
            databaseBook, scheduleBook, differentUserPrefs)));
    }
}
