package thrift.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setThriftFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setThriftFilePath(null));
    }

    @Test
    public void equals() {
        UserPrefs userPrefs = new UserPrefs();

        // Same object
        assertTrue(userPrefs.equals(userPrefs));

        // Different object
        assertFalse(userPrefs.equals("Userprefs"));

        // Null object
        assertFalse(userPrefs.equals(null));
    }

}
