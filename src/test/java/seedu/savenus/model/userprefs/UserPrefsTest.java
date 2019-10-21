package seedu.savenus.model.userprefs;

import static seedu.savenus.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import seedu.savenus.model.userprefs.UserPrefs;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setMenuFilePath(null));
    }

}
