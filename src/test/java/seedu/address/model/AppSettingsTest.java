package seedu.address.model;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.appsettings.AppSettings;

public class AppSettingsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setDataFilePath(null));
    }

    @Test
    public void setDefaultDifficulty_nullDifficulty_throwsNullPointerException() {
        AppSettings appSettings = new AppSettings();
        assertThrows(NullPointerException.class, () -> appSettings.setDefaultDifficulty(null));
    }

    @Test
    public void setDefaultTheme_nullTheme_throwsNullPointerException() {
        AppSettings appSettings = new AppSettings();
        assertThrows(NullPointerException.class, () -> appSettings.setDefaultTheme(null));
    }

}
