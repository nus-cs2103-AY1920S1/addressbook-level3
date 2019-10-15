package seedu.address.model;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class WorkoutPlannerUserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        WorkoutPlannerUserPrefs userPref = new WorkoutPlannerUserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setDukeCooksFilePath_nullPath_throwsNullPointerException() {
        WorkoutPlannerUserPrefs workoutPlannerUserPrefs = new WorkoutPlannerUserPrefs();
        assertThrows(NullPointerException.class, () -> workoutPlannerUserPrefs.setExercisesFilePath(null));
    }

}
