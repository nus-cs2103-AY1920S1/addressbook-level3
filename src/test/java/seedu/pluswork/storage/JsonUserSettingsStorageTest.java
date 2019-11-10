package seedu.pluswork.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.pluswork.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.pluswork.commons.exceptions.DataConversionException;
import seedu.pluswork.model.UserSettings;
import seedu.pluswork.model.settings.ClockFormat;
import seedu.pluswork.model.settings.Theme;

class JsonUserSettingsStorageTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonUserSettingsStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readUserSettings_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readUserSettings(null));
    }

    private Optional<UserSettings> readUserSettings(String userSettingsFileInTestDataFolder) throws
            DataConversionException {
        Path settingsFilePath = addToTestDataPathIfNotNull(userSettingsFileInTestDataFolder);
        return new JsonUserSettingsStorage(settingsFilePath).readUserSettings(settingsFilePath);
    }

    @Test
    public void readUserSettings_missingFile_emptyResult() throws DataConversionException {
        assertFalse(readUserSettings("NonExistentFile.json").isPresent());
    }

    @Test
    public void readUserSettings_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readUserSettings("NotJsonFormatUserSettings.json"));
    }

    private Path addToTestDataPathIfNotNull(String userSettingsFileInTestDataFolder) {
        return userSettingsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(userSettingsFileInTestDataFolder)
                : null;
    }

    @Test
    public void readUserSettings_fileInOrder_successfullyRead() throws DataConversionException {
        UserSettings expected = getTypicalUserSettings();
        UserSettings actual = readUserSettings("TypicalUserSettings.json").get();
        assertEquals(expected, actual);
    }

    @Test
    public void readUserSettings_valuesMissingFromFile_defaultValuesUsed() throws DataConversionException {
        UserSettings actual = readUserSettings("EmptyUserSettings.json").get();
        assertEquals(new UserSettings(), actual);
    }

    @Test
    public void readUserSettings_extraValuesInFile_extraValuesIgnored() throws DataConversionException {
        UserSettings expected = getTypicalUserSettings();
        UserSettings actual = readUserSettings("ExtraValuesUserSettings.json").get();

        assertEquals(expected, actual);
    }

    private UserSettings getTypicalUserSettings() {
        UserSettings userSettings = new UserSettings();
        userSettings.setUserSettingsFilePath(Paths.get("data/plusworksettings.json"));
        userSettings.setTheme(Theme.DARK);
        userSettings.setClockFormat(ClockFormat.TWENTY_FOUR);
        return userSettings;
    }

    @Test
    public void saveSettings_nullSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveUserSettings(null, "SomeFile.json"));
    }

    @Test
    public void saveUserSettings_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveUserSettings(new UserSettings(), null));
    }

    /**
     * Saves {@code userPrefs} at the specified {@code userSettingsFileInTestDataFolder} filepath.
     */
    private void saveUserSettings(UserSettings userSettings, String userSettingsFileInTestDataFolder) {
        try {
            new JsonUserSettingsStorage(addToTestDataPathIfNotNull(userSettingsFileInTestDataFolder))
                    .saveUserSettings(userSettings);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file", ioe);
        }
    }

    @Test
    public void saveUserSettings_allInOrder_success() throws DataConversionException, IOException {

        UserSettings original = new UserSettings();

        Path settingsFilePath = testFolder.resolve("TempSettings.json");
        JsonUserSettingsStorage jsonUserSettingsStorage = new JsonUserSettingsStorage(settingsFilePath);

        //Try writing when the file doesn't exist
        jsonUserSettingsStorage.saveUserSettings(original);
        UserSettings readBack = jsonUserSettingsStorage.readUserSettings().get();
        assertEquals(original, new UserSettings(readBack));

        //Modify and try saving when overwriting file
        original.setTheme(Theme.DARK);
        original.setClockFormat(ClockFormat.TWENTY_FOUR);
        jsonUserSettingsStorage.saveUserSettings(original, settingsFilePath);
        readBack = jsonUserSettingsStorage.readUserSettings().get();
        assertEquals(original, new UserSettings(readBack));

        //Modify and try saving when the file exists
        original.setTheme(Theme.LIGHT);
        original.setClockFormat(ClockFormat.TWELVE);
        jsonUserSettingsStorage.saveUserSettings(original);
        readBack = jsonUserSettingsStorage.readUserSettings().get();
        assertEquals(original, new UserSettings(readBack));
    }
}
