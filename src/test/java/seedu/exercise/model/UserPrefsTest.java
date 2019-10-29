package seedu.exercise.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.exercise.testutil.Assert.assertThrows;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.exercise.testutil.CommonTestData;
import seedu.exercise.testutil.TestUtil;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setExerciseBookFilePath(null));
    }

    @Test
    public void equalsAndHashCode_variousScenarios_success() {
        TestUtil.assertCommonEqualsTest(new UserPrefs());

        UserPrefs other = new UserPrefs();
        other.setExerciseBookFilePath(Paths.get(CommonTestData.REGIME_BOOK_FILE_NAME));

        // exercisebookfilepath different -> false/different hashcode
        assertFalse(new UserPrefs().equals(other));
        assertNotEquals(other.hashCode(), new UserPrefs().hashCode());

        // regimebookkfilepath different -> false/different hashcode
        other = new UserPrefs();
        other.setRegimeBookFilePath(Paths.get(CommonTestData.EXERCISE_BOOK_FILE_NAME));
        assertFalse(new UserPrefs().equals(other));
        assertNotEquals(other.hashCode(), new UserPrefs().hashCode());

        // propertybookfilepath different -> false/different hashcode
        other = new UserPrefs();
        other.setPropertyBookFilePath(Paths.get(CommonTestData.SCHEDULE_BOOK_FILE_NAME));
        assertFalse(new UserPrefs().equals(other));
        assertNotEquals(other.hashCode(), new UserPrefs().hashCode());

        // exercisedbfilepath different -> false/different hashcode
        other = new UserPrefs();
        other.setAllExerciseBookFilePath(Paths.get(CommonTestData.SCHEDULE_BOOK_FILE_NAME));
        assertFalse(new UserPrefs().equals(other));
        assertNotEquals(other.hashCode(), new UserPrefs().hashCode());
    }
}
