package seedu.moolah.model;

import static seedu.moolah.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setMooLahFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setMooLahFilePath(null));
    }

    @Test
    public void setAliasMappings_nullAliasMappings_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setAliasMappings(null));
    }
}
