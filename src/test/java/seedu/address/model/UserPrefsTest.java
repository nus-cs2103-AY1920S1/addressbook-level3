package seedu.address.model;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class UserPrefsTest {

    private UserPrefs userPref = new UserPrefs();

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setUserSettings_nullUserSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> userPref.setUserSettings(null));
    }

    @Test
    public void setLoanRecordsFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> userPref.setLoanRecordsFilePath(null));
    }

    @Test
    public void setCatalogFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> userPref.setCatalogFilePath(null));
    }

    @Test
    public void setBorrowerRecordsFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> userPref.setBorrowerRecordsFilePath(null));
    }

}
